package com.e_commerce.Cranzo.Service.ServiceImp;

import com.e_commerce.Cranzo.Entity.Product;
import com.e_commerce.Cranzo.Model.RedisCart;
import com.e_commerce.Cranzo.Model.RedisCartItem;
import com.e_commerce.Cranzo.Repository.ProductRepository;
import com.e_commerce.Cranzo.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImp implements CartService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ProductRepository productRepository;
    private static final String CART_PREFIX = "cart:";

    private String getCartKey(Long userId) {
        return CART_PREFIX + userId;
    }

    @Override
    public RedisCart getCart(Long userId) {
        RedisCart redisCart = (RedisCart) redisTemplate.
                opsForValue()
                .get(getCartKey(userId));

        if (redisCart == null) {
            redisCart = new RedisCart();
            redisCart.setUserId(userId);
        }
        calculateTotalPrice(redisCart);
        return redisCart;
    }

    @Override
    public void addToCart(Long userId, Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        RedisCart redisCart = getCart(userId);
        Optional<RedisCartItem> existing =
                redisCart.getItems()
                        .stream()
                        .filter(i ->
                                i.getProductId().equals(productId))
                        .findFirst();

        if(existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + quantity);
        }else {
            RedisCartItem item = new RedisCartItem();
            item.setProductId(product.getId());
            item.setProductName(product.getName());
            item.setPrice(product.getPrice().doubleValue());
            item.setQuantity(quantity);
            redisCart.getItems().add(item);
        }
        calculateTotalPrice(redisCart);
        redisTemplate.opsForValue().set(getCartKey(userId), redisCart);
    }

    @Override
    public void removeFromCart(Long userId, Long productId) {
        RedisCart redisCart = getCart(userId);
        redisCart.getItems().removeIf(i -> i.getProductId().equals(productId));
        calculateTotalPrice(redisCart);
        redisTemplate.opsForValue().set(getCartKey(userId), redisCart);
    }

    @Override
    public void updateQuantity(Long userId, Long productId, Integer quantity) {
        RedisCart redisCart = getCart(userId);
        redisCart.getItems().forEach(item -> {
            if(item.getProductId().equals(productId)) {
                item.setQuantity(quantity);
            }
        });
        calculateTotalPrice(redisCart);
        redisTemplate.opsForValue().set(getCartKey(userId), redisCart);
    }

    @Override
    public void clearCart(Long userId) {
        redisTemplate.delete(getCartKey(userId));
    }

    @Override
    public void calculateTotalPrice(RedisCart cart) {
        double total = cart.getItems()
                .stream()
                .mapToDouble(i ->
                        i.getPrice() *i.getQuantity())
                .sum();
        cart.setTotalPrice(total);
    }
}
