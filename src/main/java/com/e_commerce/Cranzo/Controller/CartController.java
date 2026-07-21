package com.e_commerce.Cranzo.Controller;

import com.e_commerce.Cranzo.Config.Security.CustomUserDetailsSecurity;
import com.e_commerce.Cranzo.Model.RedisCart;
import com.e_commerce.Cranzo.Service.CartService;
import com.e_commerce.Cranzo.Service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    private Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsSecurity customUserDetailsSecurity = (CustomUserDetailsSecurity) authentication.getPrincipal();
        return customUserDetailsSecurity.getId();
    }

    @PostMapping("/add")
    public String addItem(
            @RequestParam Long productId,
            @RequestParam Integer quantity
    ){
        cartService.addToCart(getUserId(), productId, quantity);
        return "product added successfully.";
    }

    @GetMapping
    public RedisCart getCart(){
        return cartService.getCart(getUserId());
    }

    @DeleteMapping("/remove/{productId}")
    public String removeItem(@PathVariable Long productId){
        cartService.removeFromCart(getUserId(), productId);
        return "product removed successfully.";
    }

    @DeleteMapping("/clear")
    public String clearCart(){
        cartService.clearCart(getUserId());
        return "Cart cleared";
    }

    @PutMapping("/update/{productId}")
    public String updateQuantity(@PathVariable Long productId,@RequestParam Integer quantity){
        cartService.updateQuantity(getUserId(), productId, quantity);
        return "product updated successfully.";
    }
}
