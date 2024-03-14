package com.github.yumyum.mypage.controller;

import com.github.yumyum.mypage.dto.UserDTO;
import com.github.yumyum.mypage.dto.UserMatchDTO;
import com.github.yumyum.mypage.service.UserMatchService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/api")
public class UserMatchController {

    private final UserMatchService userMatchService;

    @Operation(summary = "친구 id 검색 ")
    @PostMapping("/userIdSearch")
    public ResponseEntity<?> getUserIdSearch(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userMatchService.getUserIdSearch(userDTO));
    }

    @Operation(summary = "친구 신청 보내기 ")
    @PostMapping("/insertUserMatch")
    public ResponseEntity<?> insertUserMatch(@RequestBody UserMatchDTO userMatchDTO) {
        int insertCount = userMatchService.insertUserMatch(userMatchDTO);
        System.out.println("insertCount = " + insertCount);

        if(insertCount > 0) {
            return ResponseEntity.ok().body("친구신청이 성공적으로 이루어졌습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("친구신청에 실패했습니다.");
        }
    }

}
