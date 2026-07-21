package com.e_commerce.Cranzo.Mapper;

import com.e_commerce.Cranzo.Entity.Category;
import com.e_commerce.Cranzo.Entity.Product;
import com.e_commerce.Cranzo.dto.ProductRequest;
import com.e_commerce.Cranzo.dto.ProductResponse;

public class ProductMapper {
    private ProductMapper() {

    }
    public static ProductResponse toResponse(Product product) {
        if (product == null) {
            return null;
        }
        Category category = product.getCategory();
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .imageUrl(product.getImageUrl())
                .active(product.isActive())
                .createdAt(product.getCreatedAt())
                .categoryId(category != null ? category.getId() : null)
                .categoryName(category != null ? category.getName() : null)
                .build();
    }
    public static void applyToEntity(ProductRequest productRequest, Product product) {
       product.setName(productRequest.getName());
       product.setDescription(productRequest.getDescription());
       product.setPrice(productRequest.getPrice());
       product.setStockQuantity(productRequest.getStockQuantity());
       product.setStockQuantity(productRequest.getStockQuantity());
       product.setImageUrl(productRequest.getImageUrl());
       product.setActive(productRequest.isActive());
    }

}
