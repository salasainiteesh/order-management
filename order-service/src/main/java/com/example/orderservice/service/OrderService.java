package com.example.orderservice.service;

import com.example.orderservice.kafka.OrderProducer;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    public OrderService(OrderRepository orderRepository, OrderProducer orderProducer) { // Constructor
        this.orderRepository = orderRepository;
        this.orderProducer = orderProducer;
    }

    public Order createOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        orderProducer.sendOrder(savedOrder);
        return savedOrder;
    }
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
