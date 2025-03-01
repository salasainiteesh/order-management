package com.example.orderservice.controller

import com.example.orderservice.model.Order
import com.example.orderservice.service.OrderService
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.mockito.Mockito.*

@WebMvcTest(OrderController) // ✅ Ensures correct Spring Boot test setup
class OrderControllerSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    @MockBean
    OrderService orderService // ✅ Mock service layer

    def "POST /api/orders should create an order"() {
        given:
        def order = new Order(id: 1, name: "New Order", productName: "Laptop", quantity: 2)

        // ✅ Use `any(Order.class)` instead of `_` to match method signature
        when(orderService.createOrder(any(Order.class))).thenReturn(order)

        expect:
        mockMvc.perform(post("/api/orders")
                .contentType("application/json")
                .content('{"name":"New Order", "productName":"Laptop", "quantity":2}'))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.name').value("New Order"))
                .andExpect(jsonPath('$.productName').value("Laptop"))
                .andExpect(jsonPath('$.quantity').value(2))
    }
}
