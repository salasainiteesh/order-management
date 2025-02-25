package com.example.orderservice.repository

import com.example.orderservice.model.Order
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
class OrderRepositorySpec extends Specification {

    @Autowired
    OrderRepository orderRepository

    def "should save an order and retrieve it from the database"() {
        given: "An order object"
        def order = new Order(productName: "Laptop", quantity: 2)

        when: "The order is saved"
        def savedOrder = orderRepository.save(order)

        then: "The order should be saved successfully"
        savedOrder != null
        savedOrder.id > 0
        savedOrder.productName == "Laptop"
    }
}

