package com.example.Order.Member.service;

import com.example.Order.Member.domain.*;
import com.example.Order.Member.repository.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberRepo memberRepo;

    @Autowired
    public MemberService(MemberRepo memberRepo){
        this.memberRepo = memberRepo;
    }


    public MemberSaveResDto memberSave(MemberSaveReqDto memberSaveReqDto) throws IllegalArgumentException{
        //TODO : 같은 이메일일 경우 예외 던지기.
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
}
