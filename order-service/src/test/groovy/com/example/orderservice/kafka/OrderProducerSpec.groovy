package com.example.orderservice.kafka

import com.example.orderservice.model.Order
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.KafkaTemplate
import spock.lang.Specification



@SpringBootTest
class OrderProducerSpec extends Specification {

    def kafkaTemplate = Mock(KafkaTemplate)
    def orderProducer = new OrderProducer(kafkaTemplate) // Ensure it's instantiated correctly

    def "should send an order message"() {
        given: "An order message"
        def order = new Order(id: 1L, productName: "Laptop", quantity: 3)

        when: "Sending the message"
        boolean result = orderProducer.sendOrder(order) // Call on instance, NOT statically

        then: "The message should be sent successfully"
        result == true
    }
}
