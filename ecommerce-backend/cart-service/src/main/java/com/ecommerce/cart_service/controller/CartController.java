package com.ecommerce.cart_service.controller;

import com.ecommerce.cart_service.dto.CartItemDTO;
import com.ecommerce.cart_service.entity.CartItem;
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
        cartItemDTO.setAddedAt(LocalDateTime.now());
        CartItemDTO savedItem = cartService.addToCart(cartItemDTO);
        return ResponseEntity.ok(savedItem);
    }

    // ✅ 2. Get all cart items for a user
    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItemDTO>> getCart(@PathVariable Long userId) {
        List<CartItemDTO> items = cartService.getCartItemsByUserId(userId);
        return ResponseEntity.ok(items);
    }

    // ✅ 3. Update quantity of a product in cart
    @PutMapping("/update/{id}")
    public ResponseEntity<CartItemDTO> updateQuantity(
            @PathVariable Long id,
            @RequestParam int quantity) {
        CartItemDTO updatedItem = cartService.updateQuantity(id, quantity);
        return ResponseEntity.ok(updatedItem);
    }

    // ✅ 4. Delete a specific cart item
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Long id) {
        cartService.deleteCartItem(id);
        return ResponseEntity.ok("Cart item deleted successfully.");
    }

    // ✅ 5. Clear all cart items for a user
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<String> clearCart(@PathVariable Long userId) {
        cartService.clearCartByUserId(userId);
        return ResponseEntity.ok("Cart cleared successfully for user: " + userId);
    }
}
