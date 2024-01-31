package com.example.Order.Member.service;

import com.example.Order.Member.domain.Member;
import com.example.Order.Member.dto.MemberSaveReqDto;
import com.example.Order.Member.domain.Role;
import com.example.Order.Member.repository.MemberRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @MockBean
    private MemberRepo memberRepo;

    @Test
    void memberSaveTest() {
        Member member = Member.builder()
                .name("재영")
                .email("재영@네이버")
                .password("1234")
                .address("헬스장")
                .role(Role.USER)
                .build();
        Mockito.when(memberRepo.save(member)).thenReturn(member);
        MemberSaveReqDto memberSaveReqDto = new MemberSaveReqDto();
        memberSaveReqDto.setName("재영");
        memberSaveReqDto.setEmail("재영@네이버");
        memberSaveReqDto.setPassword("1234");
        memberSaveReqDto.setAddress("헬스장");
        MemberSaveDto memberSaveDto =memberService.memberSave(memberSaveReqDto);
        Assertions.assertEquals(memberSaveDto.getName(),member.getName());
    }

}