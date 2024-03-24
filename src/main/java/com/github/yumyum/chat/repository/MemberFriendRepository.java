package com.github.yumyum.chat.repository;

import com.github.yumyum.chat.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberFriendRepository extends JpaRepository<Member, Integer> {

    List<Member> findAll();
}
