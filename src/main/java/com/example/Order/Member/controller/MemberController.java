package com.example.Order.Member.controller;

import com.example.Order.Member.domain.Member;
import com.example.Order.Member.domain.MemberListRepDto;
import com.example.Order.Member.domain.MemberSaveReqDto;
import com.example.Order.Member.domain.MemberSaveResDto;
import com.example.Order.Member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;


@RestController
public class MemberController {

    private final MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    //TODO : 회원가입 후 가입한 회원의 정보를 다시 리턴해줄까요 말까요
    @PostMapping("member/new")
    public MemberSaveResDto memberCreate(@RequestBody MemberSaveReqDto memberSaveReqDto){
        try {
            return memberService.memberSave(memberSaveReqDto);
        }catch (IllegalArgumentException e){
            e.getStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    @GetMapping("members")
    public List<MemberListRepDto> findAll(){
        return memberService.findAll();
    }

    @GetMapping("member/{id}/orders")
    public List<Objects> showOrders(){
        return null;
    }
}
