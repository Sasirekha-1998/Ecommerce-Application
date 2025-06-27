package com.ecommerce.product_service.service;


import com.ecommerce.product_service.dto.ProductDTO;
import com.ecommerce.product_service.entity.Product;
import com.ecommerce.product_service.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepository productRepository;

//    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
//        this.productRepository = productRepository;
//        this.modelMapper = modelMapper;
//    }


    public List<ProductDTO> fetchAllProducts(String productId) {
        log.info("inside fetchAllProducts");
        List<Product> productList = new ArrayList<>();
        if (Objects.isNull(productId)) {
            productList = productRepository.findAll();
        } else {
            Optional<Product> productForId = productRepository.findById(Long.valueOf(productId));
            if (productForId.isPresent()) {
                productList.add(productForId.get());
            }
        }
        List<ProductDTO> productDTOS = productList.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        return productDTOS;
    }

    public Product insertProduct(ProductDTO productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        return productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public ProductDTO updateProduct(ProductDTO productDTO) {
        Product givenProduct = modelMapper.map(productDTO, Product.class);
        Product updatedProduct = productRepository.save(givenProduct);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    public ProductDTO patchProduct(Long id, Map<String, Object> updates) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "name" -> product.setName((String) value);
                case "description" -> product.setDescription((String) value);
                case "price" -> product.setPrice(Double.valueOf(value.toString()));
                case "imageUrl" -> product.setImageUrl((String) value);
                default -> throw new IllegalArgumentException("Invalid field: " + key);
            }
        });

        Product updated = productRepository.save(product);
        return modelMapper.map(updated, ProductDTO.class);
    }

}
