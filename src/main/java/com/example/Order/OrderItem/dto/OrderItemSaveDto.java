package com.example.Order.OrderItem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemSaveDto {
    private Long itemId;
    private int count;
}
