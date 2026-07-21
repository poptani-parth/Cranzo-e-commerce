package com.e_commerce.Cranzo.Service;

import com.e_commerce.Cranzo.dto.ProductRequest;
import com.e_commerce.Cranzo.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface ProductService {

        ProductResponse createProduct(ProductRequest productRequest);

        ProductResponse updateProduct(Long productId, ProductRequest productRequest);

        void deleteProduct(Long id);

        ProductResponse getProductById(Long id);

        Page<ProductResponse> getAllProducts(Pageable pageable);

        Page<ProductResponse> searchProducts(String keyword, Pageable pageable);

        Page<ProductResponse> filterProducts(Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

}
