package com.github.yumyum.mypage.controller;

import com.github.yumyum.mypage.dto.CalendarDTO;
import com.github.yumyum.mypage.service.CalendarService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/api")
public class CalendarController {

     private final CalendarService calendarService;

    // 메모 목록 조회 (현재 달 기준)
    @Operation(summary = "메모 목록 조회")
    @PostMapping("/monthMemoList")
    public ResponseEntity<?> getMonthMemoList(@RequestBody CalendarDTO calendarDTO) {
        return ResponseEntity.ok(calendarService.getMonthMemoList(calendarDTO.getMemoDt(), calendarDTO.getUserSn()));
    }

    // 메모 상세 조회
    @Operation(summary = "메모 상세 조회")
    @PostMapping("/dayMemoList")
    public ResponseEntity<?> getDayMemoList(@RequestBody CalendarDTO calendarDTO) {
        return ResponseEntity.ok(calendarService.getDayMemoList(calendarDTO.getMemoDt(), calendarDTO.getUserSn()));
    }
    // 메모 추가
    @Operation(summary = "메모 추가")
    @PostMapping("/insertMemo")
    public ResponseEntity<?> insertMemo(@RequestBody CalendarDTO calendarDTO) {
        return ResponseEntity.ok(calendarService.insertMemo(calendarDTO));

    }
    // 메모 수정
    @Operation(summary = "메모 수정")
    @PostMapping("/updateMemo")
    public ResponseEntity<?> updateMemo(@RequestBody CalendarDTO calendarDTO) {
        return ResponseEntity.ok(calendarService.updateMemo(calendarDTO));

    }
    // 메모 삭제
    @Operation(summary = "메모 삭제")
    @PostMapping("/deleteMemo")
    public ResponseEntity<?> deleteMemo(@RequestBody CalendarDTO calendarDTO) {
        return ResponseEntity.ok(calendarService.deleteMemo(calendarDTO));

    }


}
