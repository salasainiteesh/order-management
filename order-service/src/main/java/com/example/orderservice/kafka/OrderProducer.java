package com.example.orderservice.kafka;

import com.example.orderservice.model.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private final KafkaTemplate<String, Order> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderMessage(Order order) { // ✅ Ensure this method exists
        kafkaTemplate.send("orders", order);
    }
}
