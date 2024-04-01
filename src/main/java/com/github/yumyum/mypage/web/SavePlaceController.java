package com.github.yumyum.mypage.web;

import com.github.yumyum.mypage.dto.SaveReqDTO;
import com.github.yumyum.mypage.service.SavePlaceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/api")
public class SavePlaceController {
    private final SavePlaceService savePlaceService;

    // 유저 즐겨찾기 목록 조회
    @Operation(summary = "즐겨찾기 목록 조회")
    @GetMapping("/savePlaceList/{id}")
    public ResponseEntity<?> getSavePlaceList(@PathVariable int id) {
        return ResponseEntity.ok(savePlaceService.getSavePlaceList(id));
    }

    // 즐겨찾기 추가
    @Operation(summary = "즐겨찾기 추가")
    @PostMapping("/addSavePlace")
    public ResponseEntity<?> addSavePlace(@RequestBody SaveReqDTO saveReqDTO) {
        return ResponseEntity.ok(savePlaceService.addSavePlace(saveReqDTO));
    }

    // 즐겨찾기 해제
    @Operation(summary = "즐겨찾기 해제")
    @DeleteMapping("/delSavePlace/{saveSn}")
    public ResponseEntity<?> delSavePlace(@PathVariable int saveSn) {
        return ResponseEntity.ok(savePlaceService.delSavePlace(saveSn));
    }

}
