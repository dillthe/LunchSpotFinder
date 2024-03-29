package com.github.yumyum.mypage.controller;

import com.github.yumyum.mypage.dto.SavePlaceDTO;
import com.github.yumyum.mypage.service.SavePlaceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/api")
public class SavePlaceController {
    private final SavePlaceService savePlaceService;

    // 유저 즐겨찾기 목록 조회
    @Operation(summary = "즐겨찾기 목록 조회")
    @PostMapping("/savePlaceList")
    public ResponseEntity<?> getSavePlaceList(@RequestBody SavePlaceDTO savePlaceDTO) {
        return ResponseEntity.ok(savePlaceService.getSavePlaceList(savePlaceDTO));
    }

    // 즐겨찾기 추가
    @Operation(summary = "즐겨찾기 추가")
    @PostMapping("/addSavePlace")
    public ResponseEntity<?> addSavePlace(@RequestBody SavePlaceDTO savePlaceDTO) {
        return ResponseEntity.ok(savePlaceService.addSavePlace(savePlaceDTO));
    }

    // 즐겨찾기 해제
    @Operation(summary = "즐겨찾기 해제")
    @PostMapping("/delSavePlace")
    public ResponseEntity<?> delSavePlace(@RequestBody SavePlaceDTO savePlaceDTO) {
        return ResponseEntity.ok(savePlaceService.delSavePlace(savePlaceDTO));
    }

}
