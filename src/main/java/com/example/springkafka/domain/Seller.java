package com.example.springkafka.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Seller {
    private String name;

    private String contact;

    public Seller(String name) {
        this.name = name;
        this.contact = "telephone";
    }
}
