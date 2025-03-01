package com.example.orderservice.service;

import com.example.orderservice.model.Inventory;
import com.example.orderservice.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * Get inventory by product name.
     */
    public Optional<Inventory> getInventoryByProduct(String productName) {
        return inventoryRepository.findByProductName(productName);
    }

    /**
     * Reduce inventory quantity after an order is placed.
     */
    public void reduceInventory(String productName, int quantity) {
        Optional<Inventory> inventoryOpt = inventoryRepository.findByProductName(productName);

        if (inventoryOpt.isPresent()) {
            Inventory inventory = inventoryOpt.get();
            if (inventory.getQuantity() >= quantity) {
                inventory.setQuantity(inventory.getQuantity() - quantity);
                inventoryRepository.save(inventory);
            } else {
                throw new RuntimeException("Not enough stock for product: " + productName);
            }
        } else {
            throw new RuntimeException("Inventory not found for product: " + productName);
        }
    }

    /**
     * Increase inventory stock.
     */
    public void increaseInventory(String productName, int quantity) {
        Optional<Inventory> inventoryOpt = inventoryRepository.findByProductName(productName);

        if (inventoryOpt.isPresent()) {
            Inventory inventory = inventoryOpt.get();
            inventory.setQuantity(inventory.getQuantity() + quantity);
            inventoryRepository.save(inventory);
        } else {
            Inventory newInventory = new Inventory(productName, quantity);
            inventoryRepository.save(newInventory);
        }
    }

}
