package com.example.Order.Member.repository;

import com.example.Order.Member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepo extends JpaRepository<Member,Long> {

    Optional<Member> findByEmail(String email);
}
