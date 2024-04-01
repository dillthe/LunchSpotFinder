package com.github.yumyum.mypage.repository;

import com.github.yumyum.mypage.repository.entity.UserMatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserMatchRepository extends JpaRepository<UserMatchEntity, Integer> {

    @Query("select u from UserMatchEntity u where (u.sendUser.id = :id and u.receiveUser.id = :matchId) or (u.sendUser.id = :matchId and u.receiveUser.id = :id) ORDER BY u.sendDt DESC LIMIT 1")
    UserMatchEntity matchState(int id, int matchId);

    @Query("select u from UserMatchEntity u where u.receiveUser.id = :id and u.matchCode = 'W' ORDER BY u.sendDt DESC")
    List<UserMatchEntity> findByReceiveUser(int id);

//    @Modifying
//    @Query("update UserMatchEntity u set u.matchCode = :matchCode where u.matchSn = :matchSn")
//    int updateMatchCode(int matchSn, char matchCode);
}
