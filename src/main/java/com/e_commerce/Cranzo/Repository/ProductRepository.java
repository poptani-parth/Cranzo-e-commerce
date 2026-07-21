package com.e_commerce.Cranzo.Repository;

import com.e_commerce.Cranzo.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    // simple keyword search across name (case-insensitive), Paginated
    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    // category filter, paginated
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    // filter by  price range, paginated
    Page<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    //only active
    Page<Product> findByActiveTrue(Pageable pageable);
}
