package com.ecommerce.ecommerce.controllers;

import com.ecommerce.ecommerce.dto.ProductDto;
import com.ecommerce.ecommerce.models.*;
import com.ecommerce.ecommerce.repositories.*;
import com.ecommerce.ecommerce.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductControllerTest {

    private ProductRepository productRepository;
    private ProductService productService;
    private CategoryRepository categoryRepo;
    private ColorRepository colorRepo;
    private SizeRepository sizeRepo;
    private BrandRepository brandRepo;
    private ProductController productController;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {

        this.productRepository = mock(ProductRepository.class);
        this.productService = new ProductService(productRepository);
        this.categoryRepo = mock(CategoryRepository.class);
        this.colorRepo = mock(ColorRepository.class);
        this.sizeRepo = mock(SizeRepository.class);
        this.brandRepo = mock(BrandRepository.class);

        productRepository.deleteAll();

        this.productController = new ProductController(productRepository, productService, categoryRepo, colorRepo, sizeRepo, brandRepo);

        this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void contextLoads() {
        assertThat(productController).isNotNull();
    }

    @Test
    public void testGetAllProducts() throws Exception {
        Product product = new Product(1L, "Product1", "Description1", 100.0);

        when(productRepository.findAll()).thenReturn(List.of(product));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("[0].name", equalTo("Product1")))
                .andExpect(jsonPath("[0].price", equalTo(100.0)));
    }

    @Test
    public void testGetProductById_Success() throws Exception {
        Product product = new Product(1L, "Product1", "Description1", 100.0);
        product.setCategories(new ArrayList<>());  // Asigna una lista vacía de categorías
        product.setBrand(new Brand());
        product.setColors(new ArrayList<>());
        product.setSizes(new ArrayList<>());

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        mockMvc.perform(get("/product/{id}", product.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Product1")))
                .andExpect(jsonPath("$.description", equalTo("Description1")));
    }

    @Test
    public void testCreateProduct() throws Exception {
        // Crear el JSON para enviar al endpoint
        String productJson = "{\n" +
                "    \"name\": \"Pro1\",\n" +
                "    \"description\": \"Des1\",\n" +
                "    \"price\": 100.0,\n" +
                "    \"categories\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"category\": \"test\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"brand\": {\n" +
                "        \"id\": 1,\n" +
                "        \"brand\": \"test\"\n" +
                "    },\n" +
                "    \"sizes\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"size\": \"test\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"colors\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"color\": \"test\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        Product mockProduct = new Product();
        mockProduct.setName("Pro1");
        mockProduct.setDescription("Des1");
        mockProduct.setPrice(100.0);
        Category category = new Category(1L, "test");

        List<Category> categories = new ArrayList<>();
        categories.add(category);
        mockProduct.setCategories(categories);

        Brand brand = new Brand(1L, "test");
        mockProduct.setBrand(brand);

        Size size = new Size(1L, "test");
        List<Size> sizes = new ArrayList<>();
        sizes.add(size);
        mockProduct.setSizes(sizes);

        Color color = new Color(1L, "test");
        List<Color> colors = new ArrayList<>();
        colors.add(color);
        mockProduct.setColors(colors);

        when(productRepository.save(any(Product.class))).thenReturn(mockProduct);

        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isCreated())  // Verifica que el código de estado sea 201
                .andExpect(jsonPath("$.name", equalTo("Pro1")))  // Verifica el campo name
                .andExpect(jsonPath("$.description", equalTo("Des1")))  // Verifica el campo description
                .andExpect(jsonPath("$.categories[0].id", equalTo(1)))  // Verifica la categoría
                .andExpect(jsonPath("$.brand.id", equalTo(1)))  // Verifica la marca
                .andExpect(jsonPath("$.sizes[0].id", equalTo(1)))  // Verifica la talla
                .andExpect(jsonPath("$.colors[0].id", equalTo(1)))  // Verifica el color
                .andExpect(jsonPath("$.categories[0].category", equalTo("test")))  // Verifica category
                .andExpect(jsonPath("$.brand.brand", equalTo("test")))  // Verifica brand
                .andExpect(jsonPath("$.sizes[0].size", equalTo("test")))  // Verifica size
                .andExpect(jsonPath("$.colors[0].color", equalTo("test")));  // Verifica color
    }

    @Test
    public void testDeleteProduct_Success() throws Exception {
        Long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(true);

        mockMvc.perform(delete("/product/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", equalTo("Product con el ID " + productId + " eliminao.")));
    }
    @Test
    public void testDeleteProduct_NotFound() throws Exception {
        Long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(false);

        mockMvc.perform(delete("/product/{id}", productId))
                .andExpect(status().isNotFound());
    }

}
