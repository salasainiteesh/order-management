package com.example.orderservice.service;

import com.example.orderservice.model.Inventory;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryService inventoryService;

    @Transactional
    public Order createOrder(Order order) {

        Optional<Inventory> inventoryOpt = inventoryService.getInventoryByProduct(order.getProductName());

        if (inventoryOpt.isPresent()) {
            Inventory inventory = inventoryOpt.get();

            if (inventory.getQuantity() < order.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + order.getProductName());
            }


            inventoryService.reduceInventory(order.getProductName(), order.getQuantity());


            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Product not found in inventory: " + order.getProductName());
        }
    }
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
