package com.ecommerce.cart_service.service;

import com.ecommerce.cart_service.dto.CartItemDTO;
import com.ecommerce.cart_service.entity.CartItem;
import com.ecommerce.cart_service.repository.CartRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    public CartService(CartRepository cartRepository, ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.modelMapper = modelMapper;
    }

    public CartItemDTO addToCart(CartItemDTO cartItemDTO) {
        CartItem cartItem = modelMapper.map(cartItemDTO, CartItem.class);
        cartItem.setAddedAt(LocalDateTime.now());

        CartItem savedItem = cartRepository.save(cartItem);
        return modelMapper.map(savedItem, CartItemDTO.class);
    }

    public List<CartItemDTO> getCartItemsByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .stream()
                .map(item -> modelMapper.map(item, CartItemDTO.class))
                .toList();
    }

    @Transactional
    public CartItemDTO updateQuantity(Long userId, Long productId, Integer quantity) {
        Optional<CartItem> optionalCartItem = cartRepository.findByUserIdAndProductId(userId, productId);
        if (optionalCartItem.isEmpty()) {
            throw new RuntimeException("Cart item not found for user and product");
        }

        CartItem cartItem = optionalCartItem.get();
        cartItem.setQuantity(quantity);
        CartItem updated = cartRepository.save(cartItem);
        return modelMapper.map(updated, CartItemDTO.class);
    }

    @Transactional
    public void deleteCartItem(Long userId, Long productId) {
        cartRepository.deleteByUserIdAndProductId(userId, productId);
    }

    @Transactional
    public void deleteAllCartItems(Long userId) {
        cartRepository.deleteAllByUserId(userId);
    }

}
