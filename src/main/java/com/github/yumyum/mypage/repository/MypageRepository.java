package com.github.yumyum.mypage.repository;

import com.github.yumyum.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MypageRepository extends JpaRepository<Member, Integer> {
    // jpa 메소드로 검색
//    Optional<List<Member>> findByUserIdContaining(String userId);
    // 쿼리로 검색
//    @Query("SELECT ue FROM UserEntity ue WHERE ue.userId LIKE CONCAT('%', :userId, '%')")
//    Optional<List<UserEntity>> searchUserId(String userId);


    @Modifying
    @Transactional
    @Query("UPDATE Member u SET u.password = :password WHERE u.id = :id")
    int updatePassword(int id, String password);
}
