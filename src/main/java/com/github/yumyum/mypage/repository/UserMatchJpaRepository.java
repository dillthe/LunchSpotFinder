package com.github.yumyum.mypage.repository;

import com.github.yumyum.mypage.repository.entity.UserMatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserMatchJpaRepository extends JpaRepository<UserMatchEntity, Integer> {

//    Optional<List<UserMatchEntity>> findByReceiveUser(UserMatchEntity receiveUser);
@Query("select u from UserMatchEntity u where u.receiveUser.userSn = :receiveUserId")
List<UserMatchEntity> findByReceiveUser(int receiveUserId);
}
