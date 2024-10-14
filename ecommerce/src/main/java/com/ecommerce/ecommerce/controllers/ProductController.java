package com.ecommerce.ecommerce.controllers;


import com.ecommerce.ecommerce.dto.ProductDto;
import com.ecommerce.ecommerce.exceptions.ProductNotFoundException;
import com.ecommerce.ecommerce.mappers.ProductMapper;
import com.ecommerce.ecommerce.models.*;
import com.ecommerce.ecommerce.repositories.*;
import com.ecommerce.ecommerce.services.ProductService;
import com.ecommerce.ecommerce.services.FileStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class ProductController {
    @Autowired
    private ProductRepository productRepo;
    private ProductService productService;
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private ColorRepository colorRepo;
    @Autowired
    private SizeRepository sizeRepo;
    @Autowired
    private BrandRepository brandRepo;
    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/product")
    public ProductDto newProduct(
            @RequestParam("product") String productJson,
            @RequestParam("image") MultipartFile imageFile
    ) {
        try {
            ProductDto productDto = new ObjectMapper().readValue(productJson, ProductDto.class);

            String imageName = fileStorageService.storeFile(imageFile);

            productDto.setImagePath("/images/" + imageName);

            Product product = ProductMapper.INSTANCE.toProductEntity(productDto);
            Product savedProduct = productRepo.save(product);

            return ProductMapper.INSTANCE.toProductDto(savedProduct);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al procesar el JSON del productoooo", e);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el productoooo", e);
        }
    }

    @GetMapping("/images/{fileName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName, HttpServletRequest request) {
        Path path = Paths.get(fileStorageService.getFileStorageLocation().toString()).resolve(fileName).normalize();
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
            if(resource.exists()) {
                String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
                if(contentType == null) {
                    contentType = "application/octet-stream";
                }

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                throw new RuntimeException("No se pudo encontrar el archivo " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Error al leer el archivo " + fileName, ex);
        } catch (IOException ex) {
            throw new RuntimeException("Error al determinar el tipo de archivo.", ex);
        }
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return (List<Product>) productRepo.findAll();
    }


    @GetMapping("/product/{id}")
    public ProductDto getProductById(@PathVariable Long id) {

        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return ProductMapper.INSTANCE.toProductDto(product);
    }

    @DeleteMapping("/product/{id}")
    public String deleteProduct(@PathVariable Long id){
        if(!productRepo.existsById(id)){
            throw new ProductNotFoundException(id);
        }

        productRepo.deleteById(id);
        return "Product con el ID " + id + " eliminao.";
    }

    @PatchMapping("/product/{id}")
    public ProductDto updateProductFields(@RequestBody ProductDto productDto) {
        Product existingProduct = productRepo.findById(productDto.getId())
                .orElseThrow(() -> new ProductNotFoundException(productDto.getId()));

        if (productDto.getName() != null) {
            existingProduct.setName(productDto.getName());
        }
        if (productDto.getDescription() != null) {
            existingProduct.setDescription(productDto.getDescription());
        }
        if (productDto.getPrice() != 0) {
            existingProduct.setPrice(productDto.getPrice());
        }

        if (productDto.getBrand_id() != 0) {
            Brand brand = brandRepo.findById(productDto.getBrand_id()).get();
            existingProduct.setBrand(brand);
        }

        if (productDto.getCategories_id() != null && !productDto.getCategories_id().isEmpty()) {
            List<Category> categories = categoryRepo.findAllById(productDto.getCategories_id());
            existingProduct.setCategories(categories);
        }

        if (productDto.getColors_id() != null && !productDto.getColors_id().isEmpty()) {
            List<Color> colors = colorRepo.findAllById(productDto.getColors_id());
            existingProduct.setColors(colors);
        }

        if (productDto.getSizes_id() != null && !productDto.getSizes_id().isEmpty()) {
            List<Size> sizes = sizeRepo.findAllById(productDto.getSizes_id());
            existingProduct.setSizes(sizes);
        }

        Product updatedProduct = productRepo.save(existingProduct);

        return ProductMapper.INSTANCE.toProductDto(updatedProduct);
    }

    //GET /products/filter?brandId=1&categoryId=2&sizeId=3&colorId=4&minPrice=10.0&maxPrice=50.0
    @GetMapping("/products/filter")
    public List<Product> filterProducts(
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long sizeId,
            @RequestParam(required = false) Long colorId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice){

        try {

            return productService.filterProducts(brandId, categoryId, sizeId, colorId, minPrice, maxPrice);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al filtrar productos", e);
        }
    }

}

