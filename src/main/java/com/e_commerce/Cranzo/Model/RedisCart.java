package com.e_commerce.Cranzo.Model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RedisCart {
    private Long userId;
    private List<RedisCartItem> items = new ArrayList<>();
    private Double totalPrice = 0.0;
}
