package com.ecommerce.ecommerce.services;

import com.ecommerce.ecommerce.models.Product;
import com.ecommerce.ecommerce.models.Product_;
import com.ecommerce.ecommerce.repositories.ProductRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepo;
    public ProductService(ProductRepository productRepository){
        this.productRepo = productRepository;
    }


    public static Specification<Product> hasBrand(Long brandId) {
        return (root, query, criteriaBuilder) ->
                brandId == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get(Product_.brand), brandId);
    }

    public static Specification<Product> hasCategory(Long categoryId) {
        return (root, query, criteriaBuilder) ->
                categoryId == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get(Product_.categories), categoryId);
    }

    public static Specification<Product> hasSize(Long sizeId) {
        return (root, query, criteriaBuilder) ->
                sizeId == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get(Product_.sizes), sizeId);
    }

    public static Specification<Product> hasColor(Long colorId) {
        return (root, query, criteriaBuilder) ->
                colorId == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get(Product_.colors), colorId);
    }


    public static Specification<Product> hasPriceBetween(Double minPrice, Double maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice == null && maxPrice == null) {
                return criteriaBuilder.conjunction();
            } else if (minPrice == null) {
                return criteriaBuilder.le(root.get(Product_.price), maxPrice);
            } else if (maxPrice == null) {
                return criteriaBuilder.ge(root.get(Product_.price), minPrice);
            } else {
                return criteriaBuilder.between(root.get(Product_.price), minPrice, maxPrice);
            }
        };
    }


    public List<Product> filterProducts(Long brandId, Long categoryId, Long sizeId, Long colorId, Double minPrice, Double maxPrice) {
        Specification<Product> spec = Specification.where(hasBrand(brandId))
                .and(hasCategory(categoryId))
                .and(hasSize(sizeId))
                .and(hasColor(colorId))
                .and(hasPriceBetween(minPrice, maxPrice));

        return productRepo.findAll(spec);
    }
}
