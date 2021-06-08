package com.roberto.dscatalog.tests;

import java.time.Instant;

import com.roberto.dscatalog.dto.ProductDTO;
import com.roberto.dscatalog.entities.Category;
import com.roberto.dscatalog.entities.Product;

public class Factory {
    
    public static Product createProduct() {
        Product product = new Product(1L, "Phone", "Good Phone", 800.00, "https://img.com/img.png", Instant.now());
        product.getCategories().add(createCategory());
        return product;
    }

    public static ProductDTO createProductDTO() {
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }

    public static Category createCategory() {
        return new Category(1L, "Eletronics");
    }
}
