package com.github.yumyum.mypage.web;

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

    // 메모 추가
    @Operation(summary = "메모 추가 / calendarCn: 메모 내용, memoDt: 메모 날짜, memberId: 사용자 ID만 전달")
    @PostMapping("/insertMemo")
    public ResponseEntity<?> insertMemo(@RequestBody CalendarDTO calendarDTO) {
        return ResponseEntity.ok(calendarService.insertMemo(calendarDTO));

    }
    // 메모 목록 조회 (선택한 달 기준)
    @Operation(summary = "메모 목록 조회 / memoDt: 메모 날짜(날짜 그대로 보내면 됨 \"2024-04-01\"), memberId: 사용자 ID 전달")
    @PostMapping("/monthMemoList")
    public ResponseEntity<?> getMonthMemoList(@RequestBody CalendarDTO calendarDTO) {
        return ResponseEntity.ok(calendarService.getMonthMemoList(calendarDTO));
    }

    // 메모 상세 조회
    @Operation(summary = "메모 상세 조회 / memoDt: 메모 날짜(날짜 그대로 보내면 됨 \"2024-04-01\"), memberId: 사용자 ID 전달")
    @PostMapping("/dayMemoList")
    public ResponseEntity<?> getDayMemoList(@RequestBody CalendarDTO calendarDTO) {
        return ResponseEntity.ok(calendarService.getDayMemoList(calendarDTO));
    }
    // 메모 수정
    @Operation(summary = "메모 수정")
    @PostMapping("/updateMemo")
    public ResponseEntity<?> updateMemo(@RequestBody CalendarDTO calendarDTO) {
        return ResponseEntity.ok(calendarService.updateMemo(calendarDTO));

    }
    // 메모 삭제
    @Operation(summary = "메모 삭제")
    @DeleteMapping("/deleteMemo/{calendarSn}")
    public ResponseEntity<?> deleteMemo(@PathVariable int calendarSn){
        return ResponseEntity.ok(calendarService.deleteMemo(calendarSn));

    }


}
