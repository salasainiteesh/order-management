package com.example.orderservice.kafka

import com.example.orderservice.model.Order
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.mockito.Mockito.*

@SpringBootTest
class OrderProducerSpec extends Specification {

    @MockBean
    OrderProducer orderProducer // ✅ Correctly mock the OrderProducer

    @Autowired
    OrderProducer serviceUnderTest // ✅ Autowire the service

    def "should send an order message"() {
        given:
        def order = new Order(id: 1, name: "Test Order", productName: "Laptop", quantity: 1)

        // ✅ Properly stub method before calling
        doNothing().when(orderProducer).sendOrderMessage(order)

        when:
        serviceUnderTest.sendOrderMessage(order)

        then:
        noExceptionThrown() // ✅ Ensures that no exception occurs
        verify(orderProducer, times(1)).sendOrderMessage(order) // ✅ Verifies execution
    }
}
