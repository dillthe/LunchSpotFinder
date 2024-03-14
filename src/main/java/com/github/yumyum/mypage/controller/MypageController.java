package com.github.yumyum.mypage.controller;

import com.github.yumyum.mypage.dto.UserDTO;
import com.github.yumyum.mypage.service.MypageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/api")
public class MypageController {
    /*
      내 정보 창 controller`
     */
    private final MypageService mypageService;

    // 내 정보 불러오기
    @Operation(summary = "내정보 조회")
    @PostMapping("/myInfo")
    public ResponseEntity<?> getMyInfo(@RequestBody int userSn) {
        return ResponseEntity.ok(mypageService.getUserInfo(userSn));

    }

    // 내 정보에서 패스워드 수정하기
    @Operation(summary = "패스워드 수정")
    @PostMapping("/updatePassword")
    /*public int updatePassword(@RequestBody UserDTO userDTO) {
        return mypageService.updatePassword(userDTO);
    }*/
    public ResponseEntity<?> updatePassword(@RequestBody UserDTO userDTO) {
        int updatedCount = mypageService.updatePassword(userDTO);
        if(updatedCount > 0) {
            return ResponseEntity.ok().body("비밀번호가 성공적으로 변경되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자를 찾을 수 없습니다.");
        }
    }
    // 탈퇴하기
    @Operation(summary = "회원탈퇴")
    @PostMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestBody int userSn) {
        int deletedCount = mypageService.deleteUser(userSn);
        if(deletedCount > 0) {
            return ResponseEntity.ok().body("회원탈퇴가 성공적으로 이루어졌습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자를 찾을 수 없습니다.");
        }
    }
}
