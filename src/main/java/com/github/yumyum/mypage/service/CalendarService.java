package com.github.yumyum.mypage.service;


import com.github.yumyum.exceptions.NotFoundException;
import com.github.yumyum.mypage.dto.CalendarDTO;
import com.github.yumyum.mypage.repository.CalendarJpaRepository;
import com.github.yumyum.mypage.repository.entity.CalendarEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarService {

    private final CalendarJpaRepository calendarJpaRepository;

    public List<CalendarEntity> getMonthMemoList(Date memoDt, int userSn) {
        // 전달 받은 달 기준으로 메모 목록 조회
        return calendarJpaRepository.findByMonthMemoList(memoDt, userSn).orElseThrow(() -> new NotFoundException("검색 결과가 없습니다."));
    }

    public List<CalendarEntity> getDayMemoList(Date memoDt, int userSn) {
        // 전달 받은 일 기준으로 메모 목록 조회
        return calendarJpaRepository.findByDayMemoList(memoDt, userSn).orElseThrow(() -> new NotFoundException("검색 결과가 없습니다."));
    }

    public int insertMemo(CalendarDTO calendarDTO) {
        // DTO를 엔티티로 변환하는 과정 필요
        CalendarEntity calendarEntity = convertDtoToEntity(calendarDTO);

        // 엔티티를 DB에 저장
        calendarJpaRepository.save(calendarEntity);

        return calendarEntity.getCalendarSn();
    }

    public int updateMemo(CalendarDTO calendarDTO) {
        // DTO를 엔티티로 변환하는 과정 필요
        CalendarEntity calendarEntity = convertDtoToEntity(calendarDTO);

        // 엔티티를 DB에 저장
        calendarJpaRepository.save(calendarEntity);

        return calendarEntity.getCalendarSn();
    }

    public int deleteMemo(CalendarDTO calendarDTO) {
        // DTO를 엔티티로 변환하는 과정 필요
        CalendarEntity calendarEntity = convertDtoToEntity(calendarDTO);

        // 엔티티를 DB에 저장
        calendarJpaRepository.delete(calendarEntity);

        return calendarEntity.getCalendarSn();
    }

    private CalendarEntity convertDtoToEntity(CalendarDTO calendarDTO) {
        return CalendarEntity.builder()
                .calendarSn(calendarDTO.getCalendarSn())
                .calendarCn(calendarDTO.getCalendarCn())
                .memoDt(calendarDTO.getMemoDt())
                .regDt(calendarDTO.getRegDt())
                .userSn(calendarDTO.getUserSn())
                .build();
    }
}
