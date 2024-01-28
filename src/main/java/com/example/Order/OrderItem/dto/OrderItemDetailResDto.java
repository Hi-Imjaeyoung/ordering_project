package com.example.Order.OrderItem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OrderItemDetailResDto {
    private String name;
    private int quantity;
}
