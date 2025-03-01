package com.example.orderservice.service

import com.example.orderservice.Application
import com.example.orderservice.model.Order
import com.example.orderservice.repository.OrderRepository
import com.example.orderservice.repository.InventoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import spock.lang.Specification

import static org.mockito.Mockito.*

@SpringBootTest(classes = Application.class)
class OrderServiceSpec extends Specification {

    @Autowired
    OrderService orderService  // Actual service being tested

    @MockBean
    OrderRepository orderRepository  // Mocking the repository layer

    @MockBean
    InventoryRepository inventoryRepository // Mocking inventory repository

    def "should create an order successfully"() {
        given:
        def order = new Order(name: "Test Order", productName: "Laptop", quantity: 2)

        // Mocking the behavior of inventory check
        def inventory = Optional.of(new com.example.orderservice.model.Inventory(productName: "Laptop", quantity: 5))
        when(inventoryRepository.findByProductName("Laptop")).thenReturn(inventory)

        // Mocking the save behavior
        when(orderRepository.save(any(Order))).thenReturn(order)

        when:
        def savedOrder = orderService.createOrder(order)

        then:
        savedOrder != null
        savedOrder.productName == "Laptop"
    }
}
