package com.e_commerce.Cranzo.Service;

import com.e_commerce.Cranzo.Model.RedisCart;

public interface CartService {
    RedisCart getCart(Long userId);
    void addToCart(Long userId, Long productId, Integer quantity);
    void removeFromCart(Long userId, Long productId);
    void updateQuantity(Long userId, Long productId, Integer quantity);
    void clearCart(Long userId);
    void calculateTotalPrice(RedisCart cart);
}
