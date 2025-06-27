package com.ecommerce.product_service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter// ✅ this generates getters, setters, toString, etc.
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
}
