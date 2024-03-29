package com.github.yumyum.mypage.repository;

import com.github.yumyum.mypage.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> {
    // jpa 메소드로 검색
    Optional<List<UserEntity>> findByUserIdContaining(String userId);
    // 쿼리로 검색
    /*@Query("SELECT ue FROM UserEntity ue WHERE ue.userId LIKE CONCAT('%', :userId, '%')")
    Optional<List<UserEntity>> searchUserId(String userId);*/

    Optional<UserEntity> findByUserSn(int userSn);

    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.password = :password WHERE u.userSn = :userSn")
    int updatePassword(int userSn, String password);

    @Transactional
    int deleteByUserSn(int userSn);
}
