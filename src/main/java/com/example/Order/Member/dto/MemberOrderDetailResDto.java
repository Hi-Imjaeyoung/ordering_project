package com.example.Order.Member.dto;

import com.example.Order.OrderItem.dto.OrderItemDetailResDto;
import lombok.Data;

import java.util.List;

@Data
public class MemberOrderDetailResDto {
    private List<OrderItemDetailResDto> orderItemDetailResDtos;
    private int totalPrice;
    String orderTime;
}
