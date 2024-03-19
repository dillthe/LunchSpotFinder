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

        // 프론트에서 목록 리스트에 있는 사람에게 보내는 부분으로 user 테이블에 존재하는 사용자인지 체크가 필요할지?!
        if(insertCount > 0) {
            return ResponseEntity.ok().body("친구신청이 성공적으로 이루어졌습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("친구신청에 실패했습니다.");
        }
    }

    // 내가 받은 목록 리스트
    @Operation(summary = "내가 받은 목록 조회")
    @PostMapping("/getReceiveMatchList")
    public ResponseEntity<?> getReceiveMatchList(@RequestBody int receiveUserSn) {
        return ResponseEntity.ok(userMatchService.getReceiveMatchList(receiveUserSn));
    }

    // 친구 수락 / 거절 하기
    @Operation(summary = "친구 수락 / 거절 API")
    @PostMapping("/satMatchStatus")
    public ResponseEntity<?> satMatchStatus(@RequestBody UserMatchDTO userMatchDTO) {
        return ResponseEntity.ok(userMatchService.satMatchStatus(userMatchDTO));
    }

    // 내가 보낸 목록 리스트

    // 매칭된 친구 리스트 확인

}
