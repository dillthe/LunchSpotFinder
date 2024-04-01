package com.github.yumyum.member.repository;

import com.github.yumyum.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findByLoginId(String loginId);

    @Query("SELECT ue FROM Member ue WHERE ue.loginId LIKE CONCAT('%', :loginId, '%')")
    List<Member> getUserIdSearch(String loginId);

}
