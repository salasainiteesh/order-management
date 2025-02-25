package com.example.orderservice

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ApplicationSpec extends Specification {

    def "application context should load successfully"() {
        expect:
        true  // Ensures Spring Boot context loads
    }
}

