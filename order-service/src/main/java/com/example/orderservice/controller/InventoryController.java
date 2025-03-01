package com.example.orderservice.controller;

import com.example.orderservice.model.Inventory;
import com.example.orderservice.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /**
     * Get inventory details for a product.
     */
    @GetMapping("/{productName}")
    public ResponseEntity<Inventory> getInventory(@PathVariable String productName) {
        return inventoryService.getInventoryByProduct(productName)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    /**
     * Increase inventory stock.
     */
    @PostMapping("/increase")
    public ResponseEntity<String> increaseInventory(@RequestParam String productName, @RequestParam int quantity) {
        inventoryService.increaseInventory(productName, quantity);
        return ResponseEntity.ok("Inventory updated successfully.");
    }
}
