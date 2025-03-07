package com.example.orderservice.service;

import com.example.orderservice.model.Inventory;
import com.example.orderservice.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * Get inventory details for a product (case-insensitive).
     */
    public Optional<Inventory> getInventoryByProduct(String productName) {
        return inventoryRepository.findByProductNameIgnoreCase(productName);
    }

    /**
     * Increase inventory stock for a given product.
     */
    public void increaseInventory(String productName, int quantity) {
        Optional<Inventory> inventoryOpt = inventoryRepository.findByProductNameIgnoreCase(productName);

        if (inventoryOpt.isPresent()) {
            Inventory inventory = inventoryOpt.get();
            inventory.setQuantity(inventory.getQuantity() + quantity);
            inventoryRepository.save(inventory);
        } else {

            // Option 2: Automatically create the product instead
            Inventory newProduct = new Inventory(productName, quantity);
            inventoryRepository.save(newProduct);
        }
    }


    /**
     * Reduce inventory stock for a given product.
     */
    public void reduceInventory(String productName, int quantity) {
        Optional<Inventory> inventoryOptional = inventoryRepository.findByProductNameIgnoreCase(productName);
        if (inventoryOptional.isPresent()) {
            Inventory inventory = inventoryOptional.get();
            if (inventory.getQuantity() >= quantity) {
                inventory.setQuantity(inventory.getQuantity() - quantity);
                inventoryRepository.save(inventory);
            } else {
                throw new RuntimeException("Not enough stock for product: " + productName);
            }
        } else {
            throw new RuntimeException("Product not found in inventory: " + productName);
        }
    }

    /**
     * Add a new product or update quantity if the product already exists.
     */
    public Inventory addProduct(String productName, int quantity) {
        Optional<Inventory> existingProduct = inventoryRepository.findByProductNameIgnoreCase(productName);
        if (existingProduct.isPresent()) {
            Inventory product = existingProduct.get();
            product.setQuantity(product.getQuantity() + quantity); // Increase quantity
            return inventoryRepository.save(product);
        } else {
            Inventory newProduct = new Inventory(productName, quantity);
            return inventoryRepository.save(newProduct);
        }
    }

    /**
     * Get the full list of inventory.
     */
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }
}
