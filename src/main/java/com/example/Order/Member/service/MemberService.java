package com.example.Order.Member.service;

import com.example.Order.Item.repo.ItemRepo;
import com.example.Order.Member.domain.*;
import com.example.Order.Member.dto.MemberListRepDto;
import com.example.Order.Member.dto.MemberOrderDetailResDto;
import com.example.Order.Member.dto.MemberSaveReqDto;
import com.example.Order.Member.dto.MemberSaveResDto;
import com.example.Order.Member.repository.MemberRepo;
import com.example.Order.OrderItem.domain.OrderItem;
import com.example.Order.OrderItem.dto.OrderItemDetailResDto;
import com.example.Order.Ordering.domain.Ordering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    @Autowired
    private final MemberRepo memberRepo;
    public MemberService(MemberRepo memberRepo){
        this.memberRepo = memberRepo;
    }


    public MemberSaveResDto memberSave(MemberSaveReqDto memberSaveReqDto) throws IllegalArgumentException{
        if(memberRepo.findByEmail(memberSaveReqDto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        Role role = Role.ADMIN;
        if(memberSaveReqDto.getRole()==null|| memberSaveReqDto.getRole().equals("일반유저")){
            role = Role.USER;
        }
        System.out.println(memberSaveReqDto.getName());
        System.out.println(memberSaveReqDto.getAddress());
        Member member = Member.builder()
                            .name(memberSaveReqDto.getName())
                            .email(memberSaveReqDto.getEmail())
                            .password(memberSaveReqDto.getPassword())
                            .role(role)
                            .address(memberSaveReqDto.getAddress())
                            .build();
        memberRepo.save(member);
        MemberSaveResDto memberSaveResDto = new MemberSaveResDto();
        memberSaveResDto.setName(member.getName());
        memberSaveResDto.setEmail(member.getEmail());
        memberSaveResDto.setPassword(member.getPassword());
        memberSaveResDto.setAddress(member.getAddress());
        memberSaveResDto.setRole(member.getRole().toString());
        return memberSaveResDto;
    }

    public List<MemberListRepDto> findAll(){
        List<Member> members = memberRepo.findAll();
        return members.stream().map(a->new MemberListRepDto(a.getName(), a.getEmail(), a.getAddress(),a.getRole()))
                .collect(Collectors.toList());
    }

    public List<MemberOrderDetailResDto> showOrders(Long id) throws EntityNotFoundException{
        Member member = memberRepo.findById(id).orElseThrow(()->new EntityNotFoundException("없는 회원입니다."));
        List<Ordering> orderingList = member.getOrderingList();
        List<MemberOrderDetailResDto> memberOrderDetailResDtos = new ArrayList<>();
        for(Ordering nowOrder : orderingList){
            MemberOrderDetailResDto memberOrderDetailResDto = new MemberOrderDetailResDto();
            memberOrderDetailResDto.setOrderItemDetailResDtos(new ArrayList<>());
            int totalPrice = 0;
            for(OrderItem nowDetail:nowOrder.getOrderItems()){
                int quantity = nowDetail.getQuantity();
                String itemName = nowDetail.getItem().getName();
                int price = nowDetail.getItem().getPrice();
                memberOrderDetailResDto.getOrderItemDetailResDtos().add(new OrderItemDetailResDto(itemName,quantity));
                totalPrice+=price*quantity;
            }
            memberOrderDetailResDto.setTotalPrice(totalPrice);
            memberOrderDetailResDtos.add(memberOrderDetailResDto);
        }

        return memberOrderDetailResDtos;
    }
}
