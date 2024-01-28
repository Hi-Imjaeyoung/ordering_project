package com.example.Order.Member.dto;

import lombok.Data;

@Data
public class MemberSaveResDto {
    private String name;
    private String email;
    private String password;
    private String address;
    private String role;
}
