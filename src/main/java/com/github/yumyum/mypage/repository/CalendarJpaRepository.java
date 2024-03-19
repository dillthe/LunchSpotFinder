package com.github.yumyum.mypage.repository;

import com.github.yumyum.mypage.repository.entity.CalendarEntity;
import com.github.yumyum.mypage.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarJpaRepository extends JpaRepository<CalendarEntity, Integer> {

    // 특정 사용자의 특정 달에 해당하는 메모 리스트 가져오기
    @Query("SELECT c FROM CalendarEntity c WHERE FUNCTION('YEAR', c.memoDt) = FUNCTION('YEAR', ?1) AND FUNCTION('MONTH', c.memoDt) = FUNCTION('MONTH', ?1) AND c.userSn = ?2")
    Optional<List<CalendarEntity>> findByMonthMemoList(Date memoDt, int userSn);

    // 특정 사용자의 특정 날짜에 해당하는 메모 리스트 가져오기
    @Query("SELECT c FROM CalendarEntity c WHERE FUNCTION('YEAR', c.memoDt) = FUNCTION('YEAR', ?1) AND FUNCTION('MONTH', c.memoDt) = FUNCTION('MONTH', ?1) AND FUNCTION('DAY', c.memoDt) = FUNCTION('DAY', ?1) AND c.userSn = ?2")
    Optional<List<CalendarEntity>> findByDayMemoList(Date memoDt, int userSn);
}
