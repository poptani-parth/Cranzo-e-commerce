package com.e_commerce.Cranzo.Service.ServiceImp;

import com.e_commerce.Cranzo.Entity.Category;
import com.e_commerce.Cranzo.Entity.Product;
import com.e_commerce.Cranzo.Exception.ResourceNotFoundException;
import com.e_commerce.Cranzo.Mapper.ProductMapper;
import com.e_commerce.Cranzo.Repository.CategoryRepository;
import com.e_commerce.Cranzo.Repository.ProductRepository;
import com.e_commerce.Cranzo.Service.ProductService;
import com.e_commerce.Cranzo.Specification.ProductSpecification;
import com.e_commerce.Cranzo.dto.ProductRequest;
import com.e_commerce.Cranzo.dto.ProductResponse;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with id " + productRequest.getCategoryId()
                ));

        Product product = new Product();
        ProductMapper.applyToEntity(productRequest, product);
        product.setCategory(category);
        Product saved =  productRepository.save(product);
        return ProductMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id : " + id));

        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id : " + productRequest.getCategoryId()));

        ProductMapper.applyToEntity(productRequest, product);
        product.setCategory(category);
        Product updated = productRepository.save(product);
        return ProductMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if (! productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id : " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Product  product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id : " + id));
        return ProductMapper.toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> searchProducts(String keyword, Pageable pageable) {
        Specification<Product> specification = Specification
                .allOf(ProductSpecification.keywordMatches(keyword));

        return productRepository.findAll(specification, pageable)
                .map(ProductMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> filterProducts(Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
       Specification<Product> specification = Specification.allOf(
                ProductSpecification.hasCategory(categoryId),
                ProductSpecification.hasMinPrice(minPrice),
               ProductSpecification.hasMaxPrice(maxPrice)
       );
       return productRepository.findAll(specification, pageable)
               .map(ProductMapper::toResponse);
    }
}
