package com.example.Order.OrderItem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemSaveDto {
    private Long itemId;
    private int count;
}
