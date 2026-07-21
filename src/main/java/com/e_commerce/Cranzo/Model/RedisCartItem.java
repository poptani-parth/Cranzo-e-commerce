package com.e_commerce.Cranzo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisCartItem {
    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;
}
