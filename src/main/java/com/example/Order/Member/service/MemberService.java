package com.example.Order.Member.service;

import com.example.Order.Member.domain.*;
import com.example.Order.Member.dto.MemberListRepDto;
import com.example.Order.Member.dto.MemberLoginDto;
import com.example.Order.Member.dto.MemberOrderDetailResDto;
import com.example.Order.Member.dto.MemberSaveDto;
import com.example.Order.Member.repository.MemberRepo;
import com.example.Order.OrderItem.domain.OrderItem;
import com.example.Order.OrderItem.dto.OrderItemDetailResDto;
import com.example.Order.Ordering.domain.Ordering;
import com.example.Order.Ordering.domain.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.lang.management.MemoryManagerMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    @Autowired
    private final MemberRepo memberRepo;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    public MemberService(MemberRepo memberRepo, PasswordEncoder passwordEncoder){
        this.memberRepo = memberRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public Member memberSave(MemberSaveDto memberSaveReqDto) throws IllegalArgumentException{
        if(memberRepo.findByEmail(memberSaveReqDto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        Role role = Role.ADMIN;
        if(memberSaveReqDto.getRole()==null|| memberSaveReqDto.getRole().equals("일반유저")){
            role = Role.USER;
        }
        Member member = Member.builder()
                            .name(memberSaveReqDto.getName())
                            .email(memberSaveReqDto.getEmail())
                            .password(passwordEncoder.encode(memberSaveReqDto.getPassword()))
                            .role(role)
                            .address(memberSaveReqDto.getAddress())
                            .build();
        return memberRepo.save(member);
    }

    public List<MemberListRepDto> findAll(){
        List<Member> members = memberRepo.findAll();
        return members.stream().map(a->new MemberListRepDto(a.getName(), a.getEmail(), a.getAddress(),a.getRole().toString()))
                .collect(Collectors.toList());
    }

    public List<MemberOrderDetailResDto> showOrders(Long id) throws EntityNotFoundException{
        Member member = memberRepo.findById(id).orElseThrow(()->new EntityNotFoundException("없는 회원입니다."));
        List<Ordering> orderingList = member.getOrderingList();
        List<MemberOrderDetailResDto> memberOrderDetailResDtos = new ArrayList<>();
        for(Ordering nowOrder : orderingList){
            if(nowOrder.getOrderStatus().equals(Status.CANCELED)) continue;
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
            memberOrderDetailResDto.setOrderTime(nowOrder.getCreatedTime().toString());
            memberOrderDetailResDtos.add(memberOrderDetailResDto);

        }

        return memberOrderDetailResDtos;
    }
    public Member doLogin(MemberLoginDto memberLoginDto) throws EntityNotFoundException,IllegalArgumentException{
        Member member = memberRepo.findByEmail(memberLoginDto.getEmail()).orElseThrow(()->
                new EntityNotFoundException("그런 이메일은 진짜 없어"));
        if(!passwordEncoder.matches(memberLoginDto.getPassword(), member.getPassword())){
            throw new IllegalArgumentException("비밀번호가 틀립니다");
        }
        return member;
    }
    public MemberListRepDto myInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepo.findByEmail(authentication.getName()).orElseThrow(IllegalArgumentException::new);
        return new MemberListRepDto(member.getName(), member.getEmail(), member.getAddress(), member.getRole().toString());
    }
}
