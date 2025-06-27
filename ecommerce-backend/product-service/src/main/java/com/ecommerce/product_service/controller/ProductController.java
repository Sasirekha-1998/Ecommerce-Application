package com.ecommerce.product_service.controller;


import com.ecommerce.product_service.dto.ProductDTO;
import com.ecommerce.product_service.entity.Product;
import com.ecommerce.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(value = "/getProduct")
    public ResponseEntity<List<ProductDTO>> getAllProducts(@RequestParam(required = false) String productId) {
        List<ProductDTO> products = productService.fetchAllProducts(productId);
        return ResponseEntity.ok(products);
    }

    @PostMapping(value = "/createProduct", produces = "application/json", consumes = "application/json")
    public ResponseEntity createProduct(@RequestBody ProductDTO product) {
        Product insertedProduct = productService.insertProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product inserted successfully and id is :"+insertedProduct.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/updateProduct", consumes = "application/json")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO product = productService.updateProduct(productDTO);
        return ResponseEntity.ok(product);
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<ProductDTO> patchProduct(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        ProductDTO patchedProduct = productService.patchProduct(id, updates);
        return ResponseEntity.ok(patchedProduct);
    }

}
