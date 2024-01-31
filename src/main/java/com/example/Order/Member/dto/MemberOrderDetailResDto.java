package com.example.Order.Member.dto;

import com.example.Order.OrderItem.dto.OrderItemDetailResDto;
import lombok.Data;

import java.util.List;

//TODO : 얘가 Member DTO..에 있을 이유가 있을까요
@Data
public class MemberOrderDetailResDto {
    private List<OrderItemDetailResDto> orderItemDetailResDtos;
    private int totalPrice;
    String orderTime;
}
