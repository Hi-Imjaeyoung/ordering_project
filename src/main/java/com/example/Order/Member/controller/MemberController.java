package com.example.Order.Member.controller;

import com.example.Order.Member.domain.Member;
import com.example.Order.Member.domain.Role;
import com.example.Order.Member.dto.MemberListRepDto;
import com.example.Order.Member.dto.MemberLoginDto;
import com.example.Order.Member.dto.MemberOrderDetailResDto;
import com.example.Order.Member.dto.MemberSaveDto;
import com.example.Order.Member.service.MemberService;
import com.example.Order.common.ResponseDto;
import com.example.Order.securities.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class MemberController {

    @Autowired
    private final MemberService memberService;
    @Autowired
    private final JwtTokenProvider jwtTokenProvider;
    public MemberController(MemberService memberService, JwtTokenProvider jwtTokenProvider){
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // TODO :  AOP 사용 로그 처리
    @PostMapping("member/new")
    public ResponseEntity<ResponseDto> memberCreate(@RequestBody MemberSaveDto memberSaveDto){
        Member member = memberService.memberSave(memberSaveDto);
        return new ResponseEntity<>(new ResponseDto(HttpStatus.CREATED,"create success",member.getId()),HttpStatus.CREATED);
    }
    @PostMapping("/doLogin")
    public ResponseEntity<ResponseDto> doLogin(@RequestBody MemberLoginDto memberLoginDto){
        Member member = memberService.doLogin(memberLoginDto);
        String token = jwtTokenProvider.createToken(member.getEmail(),member.getRole().toString());
        Map<String,Object> memberInfo = new HashMap<>();
        memberInfo.put("token",token);
        return  new ResponseEntity<>(new ResponseDto(HttpStatus.OK,"login success",memberInfo),HttpStatus.OK);
    }

    @GetMapping("members")
    public List<MemberListRepDto> findAll(){
        return memberService.findAll();
    }

    //관리자가 사용
    @GetMapping("member/{id}/orders")
    public List<MemberOrderDetailResDto> showOrders(@PathVariable Long id){
        try{
            return memberService.showOrders(id);
        }catch (EntityNotFoundException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @PostMapping("member/myInfo")
    public MemberListRepDto myInfo(){
        return memberService.myInfo();
    }
}

