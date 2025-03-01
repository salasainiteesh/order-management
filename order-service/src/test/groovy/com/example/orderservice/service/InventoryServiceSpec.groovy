package com.example.orderservice.service

import com.example.orderservice.model.Inventory
import com.example.orderservice.repository.InventoryRepository
import spock.lang.Specification

class InventoryServiceSpec extends Specification {

    def inventoryRepository = Mock(InventoryRepository)
    def inventoryService = new InventoryService(inventoryRepository)

    def "should reduce inventory if stock is available"() {
        given:
        def inventory = new Inventory("Laptop", 5)
        inventoryRepository.findByProductName("Laptop") >> Optional.of(inventory)

        when:
        inventoryService.reduceInventory("Laptop", 3)

        then:
        1 * inventoryRepository.save(_) // Ensure inventory update
    }

    def "should throw exception if stock is insufficient"() {
        given:
        def inventory = new Inventory("Laptop", 2)
        inventoryRepository.findByProductName("Laptop") >> Optional.of(inventory)

        when:
        inventoryService.reduceInventory("Laptop", 3)

        then:
        thrown(RuntimeException)
    }
}
