package com.example.Order.Ordering.controller;

import com.example.Order.Ordering.domain.Ordering;
import com.example.Order.Ordering.dto.OrderListDto;
import com.example.Order.Ordering.dto.OrderingSaveReqDto;
import com.example.Order.Ordering.service.OrderingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderingController {

    private final OrderingService orderingService;
    @Autowired
    public OrderingController(OrderingService orderingService){
        this.orderingService = orderingService;
    }

    @GetMapping("orders")
    public List<OrderListDto> orders(){
        return orderingService.findAll();
    }

    @PostMapping("order/new")
    public OrderingSaveReqDto createOrder(@RequestBody OrderingSaveReqDto orderingSaveReqDto){
        orderingService.OrderingCreate(orderingSaveReqDto);
        return orderingSaveReqDto;
    }
    @PatchMapping("order/{id}/cancel")
    public void cancelOrder(@PathVariable Long id){
        orderingService.orderCancel(id);
    }
}
