package com.ecommerce.cart_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CartItemDTO {

    private Long id;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private LocalDateTime addedAt;
}
