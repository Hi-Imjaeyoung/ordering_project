package com.example.Order.Ordering.dto;

import com.example.Order.Item.domain.Item;
import com.example.Order.Member.domain.Member;
import com.example.Order.Ordering.domain.Ordering;

import java.util.List;


public class OrderingSaveReqDto {
    private Long memberId;
    List<Item> itemList;
    List<Ordering> orderingList;

}
