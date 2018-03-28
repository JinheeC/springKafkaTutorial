package com.example.springkafka.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product {
    private Long id;

    private String name;

    private Seller seller;

    public Product(Long id) {
        this.id = id;
        this.name = "test Product";
        this.seller = new Seller("testSeller");
    }
}
