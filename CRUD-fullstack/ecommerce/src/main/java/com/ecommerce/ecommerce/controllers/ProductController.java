package com.ecommerce.ecommerce.controllers;


import com.ecommerce.ecommerce.dto.ProductDto;
import com.ecommerce.ecommerce.exceptions.ProductNotFoundException;
import com.ecommerce.ecommerce.mappers.ProductMapper;
import com.ecommerce.ecommerce.models.Product;
import com.ecommerce.ecommerce.repositories.*;
import com.ecommerce.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class ProductController {
    @Autowired
    private ProductRepository productRepo;
    private ProductService productService;
    private CategoryRepository categoryRepo;
    private ColorRepository colorRepo;
    private SizeRepository sizeRepo;
    private BrandRepository brandRepo;

    @PostMapping("/product")
    public Product newProduct(@RequestBody Product newProduct) {
        return productRepo.save(newProduct);
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

        // TODO agregar para categorias y las demas cositas

        Product updatedProduct = productRepo.save(existingProduct);

        return ProductMapper.INSTANCE.toProductDto(updatedProduct);
    }

    /*@PutMapping("/product/{id}")
    public Product updateProduct(@RequestBody Product newProduct) {

        return productRepo.findById(newProduct.getId())
                .map(product -> {
                    product.setName(newProduct.getName());
                    product.setDescription(newProduct.getDescription());
                    product.setPrice(newProduct.getPrice());
                    product.setCategory(newProduct.getCategoryId());
                    product.setSizeId(newProduct.getSizeId());
                    product.setBrandId(newProduct.getBrandId());
                    product.setColorId(newProduct.getColorId());

                    return productRepo.save(product);
                }).orElseThrow(() -> new ProductNotFoundException(newProduct.getId()));
    }*/

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

