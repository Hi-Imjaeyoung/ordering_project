package com.example.Order.Member.controller;

import com.example.Order.Member.dto.MemberListRepDto;
import com.example.Order.Member.dto.MemberOrderDetailResDto;
import com.example.Order.Member.dto.MemberSaveDto;
import com.example.Order.Member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@RestController
public class MemberController {

    private final MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    // TODO :  AOP 사용 로그 처리
    @PostMapping("member/new")
    public MemberSaveDto memberCreate(@RequestBody MemberSaveDto memberSaveDto){
        return memberService.memberSave(memberSaveDto);
    }

    @GetMapping("members")
    public List<MemberListRepDto> findAll(){
        return memberService.findAll();
    }


    @GetMapping("member/{id}/orders")
    public List<MemberOrderDetailResDto> showOrders(@PathVariable Long id){
        try{
            return memberService.showOrders(id);
        }catch (EntityNotFoundException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}

