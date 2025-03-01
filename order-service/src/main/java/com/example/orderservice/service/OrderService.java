package com.example.orderservice.service;

import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    // ✅ Constructor Injection for Repository
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // ✅ Create a new order
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    // ✅ Fetch all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // ✅ Fetch a single order by ID
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // ✅ Update an order
    public Order updateOrder(Long id, Order orderDetails) {
        return orderRepository.findById(id).map(order -> {
            order.setName(orderDetails.getName());
            order.setProductName(orderDetails.getProductName());
            order.setQuantity(orderDetails.getQuantity());
            return orderRepository.save(order);
        }).orElseThrow(() -> new RuntimeException("Order not found with id " + id));
    }

    // ✅ Delete an order
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
