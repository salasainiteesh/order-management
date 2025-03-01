package com.example.orderservice.service

import com.example.orderservice.model.Order
import com.example.orderservice.repository.OrderRepository
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

import static org.mockito.Mockito.*

@SpringBootTest
class OrderServiceSpec extends Specification {

    @MockBean
    OrderRepository orderRepository // ✅ Correctly mocks repository

    @Autowired
    OrderService orderService // ✅ Injects OrderService

    def "should create an order successfully"() {
        given:
        def order = new Order(id: 1, name: "Test Order", productName: "Laptop", quantity: 1)

        // ✅ Ensures proper stubbing with thenReturn()
        when(orderRepository.save(any(Order.class))).thenReturn(order)

        when:
        def savedOrder = orderService.createOrder(order)

        then:
        savedOrder != null
        savedOrder.name == "Test Order"
        savedOrder.productName == "Laptop"
        savedOrder.quantity == 1
    }
}
