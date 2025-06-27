package com.ecommerce.cart_service.service;

import com.ecommerce.cart_service.dto.CartItemDTO;
import com.ecommerce.cart_service.entity.CartItem;
import com.ecommerce.cart_service.repository.CartRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    ModelMapper modelMapper;

    public CartItemDTO addToCart(CartItemDTO dto) {
        // If the product already exists in the user's cart, update quantity
        CartItem newCartItem =  cartRepository.findByUserIdAndProductId(dto.getUserId(), dto.getProductId())
                .map(existing -> {
                    existing.setQuantity(existing.getQuantity() + dto.getQuantity());
                    return cartRepository.save(existing);
                })
                .orElseGet(() -> {
                    CartItem newItem = modelMapper.map(dto, CartItem.class);
                    return cartRepository.save(newItem);
                });
        return modelMapper.map(newCartItem,CartItemDTO.class);
    }

}
