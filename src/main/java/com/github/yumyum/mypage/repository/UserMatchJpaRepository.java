package com.github.yumyum.mypage.repository;

import com.github.yumyum.mypage.dto.UserMatchDTO;
import com.github.yumyum.mypage.repository.entity.UserMatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMatchJpaRepository extends JpaRepository<UserMatchEntity, Integer> {

}
