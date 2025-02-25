package com.example.orderservice.kafka;

import com.example.orderservice.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {
    private static final String TOPIC = "orders";

    @Autowired
    private  KafkaTemplate<String, Order> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public boolean sendOrder(Order order) {
        kafkaTemplate.send(TOPIC, order);
        return true;
    }
}
