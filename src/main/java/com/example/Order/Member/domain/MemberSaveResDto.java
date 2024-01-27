package com.example.Order.Member.domain;

import lombok.Data;

// 가입 후 가입된 정보 일부를 다시 사용자에게 전달
// TODO : 다시 정보를 프론트에 전달하는 것은 활용가능성이 높음
//      1. 가입 완료 메세지에 이름을 넣어주거나
//      2. 가입과 동시에 로그인 등등
@Data
public class MemberSaveResDto {
    private String name;
    private String email;
    private String password;
    private String address;
    private String role;
}
