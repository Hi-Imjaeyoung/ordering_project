package com.example.Order.Ordering.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderListDto {
    private String memberName;
    private String status;
    LocalDateTime orderTime;
}
