package com.example.springkafka.controller;

import com.example.springkafka.producer.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class KafkaController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public void controlKafka(@PathVariable Long id) {
        productService.generateAndSendToKafka(id);
    }
}
