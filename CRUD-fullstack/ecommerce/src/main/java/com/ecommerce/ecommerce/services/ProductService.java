package com.ecommerce.ecommerce.services;

import com.ecommerce.ecommerce.models.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    public static Specification<Product> hasBrand(Long brandId) {
        return (root, query, criteriaBuilder) ->
                brandId == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("brandId"), brandId);
    }

    public static Specification<Product> hasCategory(Long categoryId) {
        return (root, query, criteriaBuilder) ->
                categoryId == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("categoryId"), categoryId);
    }

    public static Specification<Product> hasSize(Long sizeId) {
        return (root, query, criteriaBuilder) ->
                sizeId == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("sizeId"), sizeId);
    }

    public static Specification<Product> hasColor(Long colorId) {
        return (root, query, criteriaBuilder) ->
                colorId == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("colorId"), colorId);
    }


    public static Specification<Product> hasPriceBetween(Double minPrice, Double maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice == null && maxPrice == null) {
                return criteriaBuilder.conjunction();
            } else if (minPrice == null) {
                return criteriaBuilder.le(root.get("price"), maxPrice);
            } else if (maxPrice == null) {
                return criteriaBuilder.ge(root.get("price"), minPrice);
            } else {
                return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
            }
        };
    }
}
