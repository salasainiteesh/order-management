package com.example.orderservice.kafka;

import com.example.orderservice.model.Inventory;
import com.example.orderservice.model.Order;
import com.example.orderservice.service.InventoryService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderProducer {

    private final KafkaTemplate<String, Order> kafkaTemplate;
    private final InventoryService inventoryService;

    public OrderProducer(KafkaTemplate<String, Order> kafkaTemplate, InventoryService inventoryService) {
        this.kafkaTemplate = kafkaTemplate;
        this.inventoryService = inventoryService;
    }

    /**
     * Sends an order message to Kafka after checking inventory.
     */
    public void sendOrderMessage(Order order) {
        // ✅ Correctly unwrap Optional<Inventory> before using
        Optional<Inventory> inventoryOpt = inventoryService.getInventoryByProduct(order.getProductName());

        if (inventoryOpt.isPresent()) {
            Inventory inventory = inventoryOpt.get();
            int availableQuantity = inventory.getQuantity(); // ✅ Now it's an int

            if (availableQuantity >= order.getQuantity()) {
                kafkaTemplate.send("order-topic", order);
            } else {
                throw new RuntimeException("Not enough stock for product: " + order.getProductName());
            }
        } else {
            throw new RuntimeException("Inventory not found for product: " + order.getProductName());
        }
    }
}
