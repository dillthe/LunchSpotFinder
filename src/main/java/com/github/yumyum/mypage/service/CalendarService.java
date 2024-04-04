package com.github.yumyum.mypage.service;


import com.github.yumyum.exceptions.NotFoundException;
import com.github.yumyum.member.entity.Member;
import com.github.yumyum.member.repository.MemberRepository;
import com.github.yumyum.mypage.dto.CalendarDTO;
import com.github.yumyum.mypage.repository.CalendarRepository;
import com.github.yumyum.mypage.repository.entity.CalendarEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarService {
    private final MemberRepository memberRepository;

    private final CalendarRepository calendarRepository;

    public int insertMemo(CalendarDTO calendarDTO) {
        // DTO를 엔티티로 변환하는 과정 필요
        Member member = memberRepository.findById(calendarDTO.getMemberId()).orElseThrow(() -> new NotFoundException("사용자 정보가 없습니다."));

        CalendarEntity calendarEntity = CalendarEntity.builder()
                .calendarCn(calendarDTO.getCalendarCn())
                .memoDt(calendarDTO.getMemoDt())
                .member(member) // member 필드 추가
                .build();

        // 엔티티를 DB에 저장
        calendarRepository.save(calendarEntity);

        return calendarEntity.getCalendarSn();
    }

    public List<CalendarEntity> getMonthMemoList(CalendarDTO calendarDTO) {

        return calendarRepository.findByMonthMemoList(calendarDTO.getMemoDt(), calendarDTO.getMemberId()).orElseThrow(() -> new NotFoundException("검색 결과가 없습니다."));
    }

    public List<CalendarEntity> getDayMemoList(CalendarDTO calendarDTO) {
        // 전달 받은 일 기준으로 메모 목록 조회
        return calendarRepository.findByDayMemoList(calendarDTO.getMemoDt(), calendarDTO.getMemberId()).orElseThrow(() -> new NotFoundException("검색 결과가 없습니다."));
    }

    public int updateMemo(CalendarDTO calendarDTO) {

        CalendarEntity calendarEntity = calendarRepository.findById(calendarDTO.getCalendarSn()).orElseThrow(() -> new NotFoundException("메모 정보가 없습니다."));
        System.out.println(calendarEntity);

        calendarEntity.setCalendarCn(calendarDTO.getCalendarCn());
        calendarEntity.setMemoDt(calendarDTO.getMemoDt());

        calendarRepository.save(calendarEntity);

        return calendarEntity.getCalendarSn();
    }

    public int deleteMemo(int calendarSn) {
        // calendarSn을 통해 CalendarEntity 조회
        CalendarEntity calendarEntity = calendarRepository.findById(calendarSn).orElseThrow(() -> new NotFoundException("메모 정보가 없습니다."));

        // CalendarEntity 삭제
        calendarRepository.delete(calendarEntity);

        return calendarSn;
    }
}