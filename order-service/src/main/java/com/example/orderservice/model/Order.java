package com.example.orderservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders") // ✅ Change table name to avoid SQL conflict
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // ✅ Ensure this field exists if tests use it
    private String productName;  // ✅ Ensure this matches test expectations
    private int quantity;
}
