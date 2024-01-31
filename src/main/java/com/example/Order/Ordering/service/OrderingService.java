package com.example.Order.Ordering.service;

import com.example.Order.Item.domain.Item;
import com.example.Order.Item.repo.ItemRepo;
import com.example.Order.Member.repository.MemberRepo;
import com.example.Order.OrderItem.domain.OrderItem;
import com.example.Order.OrderItem.dto.OrderItemSaveDto;
import com.example.Order.Ordering.domain.Ordering;
import com.example.Order.Ordering.dto.OrderListDto;
import com.example.Order.Ordering.dto.OrderingSaveReqDto;
import com.example.Order.Ordering.repository.OrderingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderingService {

    @Autowired
    private MemberRepo memberRepo;
    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private final OrderingRepo orderingRepo;
    public OrderingService(OrderingRepo orderingRepo){
        this.orderingRepo =orderingRepo;
    }

    @Transactional
    public void OrderingCreate(OrderingSaveReqDto orderingSaveReqDto) throws IllegalArgumentException{
        List<OrderItem> orderItems = new ArrayList<>();
        for(OrderItemSaveDto now:orderingSaveReqDto.getOrderItems()){
            Item item = itemRepo.findById(now.getItemId()).orElseThrow(()->new IllegalArgumentException("없는 아이템입니다"));
            item.buy(now.getCount());
            orderItems.add(OrderItem.builder()
                    .item(item)
                    .quantity(now.getCount())
                    .build());
        }
        Ordering ordering = new Ordering(memberRepo.findById(orderingSaveReqDto.getMemberId()).orElseThrow(()->new IllegalArgumentException("없는 회원입니다"))
                ,orderItems);
        orderingRepo.save(ordering);
    }

    @Transactional
    public void orderCancel(Long id){
        Ordering ordering = orderingRepo.findById(id).orElseThrow(
                ()->new EntityNotFoundException("해당 주문이 존재하지 않습니다"));
        ordering.cancelOrder();
        List<OrderItem> orderItems = ordering.getOrderItems();
        for(OrderItem orderItem : orderItems){
            itemRepo.findById(orderItem.getItem().getId()).orElseThrow(()->new EntityNotFoundException("해당 아이템이 존재하지 않습니다."))
                    .rollBack(orderItem.getQuantity());
        }
    }

    public List<OrderListDto> findAll(){
        List<Ordering> orderings = orderingRepo.findByOrderByCreatedTimeDesc();
        List<OrderListDto> orderListDtos = new ArrayList<>();
        for(Ordering nowOrder : orderings){
            orderListDtos.add(new OrderListDto(nowOrder.getMember().getName(),nowOrder.getOrderStatus().toString(),nowOrder.getCreatedTime()));
        }
        return orderListDtos;
    }
}
