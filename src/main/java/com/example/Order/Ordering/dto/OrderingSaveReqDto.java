package com.example.Order.Ordering.dto;

import com.example.Order.OrderItem.dto.OrderItemSaveDto;
import lombok.Data;

import java.util.List;

@Data
public class OrderingSaveReqDto {
    private Long memberId;
    private List<OrderItemSaveDto> orderItems;
}
