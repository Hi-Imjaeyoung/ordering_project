package com.example.Order.Member.dto;

import lombok.Data;

@Data
public class MemberSaveReqDto {
    private String name;
    private String email;
    private String password;
    private String address;
    private String role;
}
