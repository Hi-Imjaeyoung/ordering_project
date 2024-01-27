package com.example.Order.Ordering;

import com.example.Order.Ordering.domain.Ordering;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderingController {

    @GetMapping("orders")
    public List<Object> orders(){
        return null;
    }


    //{
//    "memberId": 1,
//    "orderItems": [
//        {"itemId": 101, "count": 2},
//        {"itemId": 102, "count": 1}
//    ]
//}

    @PostMapping("order/new")
    public Ordering createOrder(@ResponseBody ){
        return null;
    }
    @PatchMapping("order/{id}/cancel")
    public Ordering cancelOrder(){
        return null;
    }
}
