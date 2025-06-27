package com.ecommerce.cart_service.controller;

import com.ecommerce.cart_service.dto.CartItemDTO;
import com.ecommerce.cart_service.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ModelMapper modelMapper;

    // ✅ 1. Add a product to the cart
    @PostMapping("/add")
    public ResponseEntity<CartItemDTO> addCart(@RequestBody CartItemDTO cartItemDTO) {
        CartItemDTO savedItem = cartService.addToCart(cartItemDTO);
        return ResponseEntity.ok(savedItem);
    }

    // ✅ 2. Get all cart items for a user
    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItemDTO>> getCart(@PathVariable Long userId) {
        List<CartItemDTO> items = cartService.getCartItemsByUserId(userId);
        return ResponseEntity.ok(items);
    }

    @PutMapping("/update")
    public ResponseEntity<CartItemDTO> updateQuantity(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam Integer quantity) {

        CartItemDTO updated = cartService.updateQuantity(userId, productId, quantity);
        return ResponseEntity.ok(updated);
    }

    // ✅ 4. Delete a specific cart item
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteItem(
            @RequestParam Long userId,
            @RequestParam Long productId) {
        cartService.deleteCartItem(userId, productId);
        return ResponseEntity.noContent().build();
    }


    // ✅ 5. Clear all cart items for a user
    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllItems(
            @RequestParam Long userId) {
        cartService.deleteAllCartItems(userId);
        return ResponseEntity.noContent().build();
    }

}
