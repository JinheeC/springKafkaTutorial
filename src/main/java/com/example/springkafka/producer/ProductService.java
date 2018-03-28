package com.example.springkafka.producer;

import com.example.springkafka.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProduceExample produceExample;

    public void generateAndSendToKafka(Long id) {
        Product product = new Product(id);
        produceExample.produce(product);
    }
}
