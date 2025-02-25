package com.example.orderservice.controller

import com.example.orderservice.model.Order
import com.example.orderservice.service.OrderService
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import spock.lang.Specification
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.beans.factory.annotation.Autowired

@WebMvcTest(OrderController)
class OrderControllerSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    @MockBean
    OrderService orderService  // ✅ Ensure this is correctly mocked

    def order

    def setup() {
        order = new Order(id: 1L, productName: "Smartwatch", quantity: 5)
    }

    def "POST /api/orders should create an order"() {
        given: "A valid order is prepared"
        orderService.createOrder(_) >> order  // ✅ Ensuring the mock service returns this order

        when: "The createOrder API is called"
        def response = mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content('{"productName":"Smartwatch", "quantity":5}')
        ).andReturn()

        then: "The response status should be 200 OK"
        response.response.status == 200

        and: "The response body should contain the created order"
        def jsonResponse = response.response.getContentAsString()
        assert jsonResponse.contains('"productName":"Smartwatch"')

        and: "Verify that orderService.createOrder was called exactly once"
        1 * orderService.createOrder(_) >> order  // ✅ Fix: Ensuring Spock correctly verifies the mock call
    }

    def "GET /api/orders should return all orders"() {
        given: "A list of orders is present"
        def orders = [
                new Order(id: 1L, productName: "Laptop", quantity: 2),
                new Order(id: 2L, productName: "Phone", quantity: 3)
        ]
        orderService.getAllOrders() >> orders  // ✅ Ensure the mock is returning the data

        when: "The getAllOrders API is called"
        def response = mockMvc.perform(get("/api/orders")).andReturn()

        then: "The response status should be 200 OK"
        response.response.status == 200

        and: "The response contains the expected orders"
        def jsonResponse = response.response.getContentAsString()
        assert jsonResponse.contains('"productName":"Laptop"')
        assert jsonResponse.contains('"productName":"Phone"')

        and: "Ensure getAllOrders was actually called"
        1 * orderService.getAllOrders()  // ✅ Fix: Ensuring Spock correctly verifies the mock call
    }
}
