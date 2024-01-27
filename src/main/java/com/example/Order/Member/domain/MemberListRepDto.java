package com.example.Order.Member.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberListRepDto {
    private String name;
    private String email;
    private String address;
    private Role role;
}