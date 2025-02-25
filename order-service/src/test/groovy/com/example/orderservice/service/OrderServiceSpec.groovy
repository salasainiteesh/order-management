package com.example.orderservice.service

import com.example.orderservice.kafka.OrderProducer
import com.example.orderservice.model.Order
import com.example.orderservice.repository.OrderRepository
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class OrderServiceSpec extends Specification {

    def orderRepository = Mock(OrderRepository)
    def orderProducer = Mock(OrderProducer)

    def orderService

    def setup() {
        orderService = new OrderService(orderRepository, orderProducer)
    }

    def "should create an order successfully"() {
        given: "An order object"
        def order = new Order(id: 1L, productName: "Smartwatch", quantity: 5)

        orderRepository.save(_) >> { Order o -> o }  // Ensure save() returns the saved order

        when: "Creating the order"
        def result = orderService.createOrder(order)

        then: "Order should be created and sent successfully"
        result == order
        1 * orderRepository.save(order) >> order
        1 * orderProducer.sendOrder(order)
    }
}
