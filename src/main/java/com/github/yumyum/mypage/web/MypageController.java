package com.github.yumyum.mypage.web;

import com.github.yumyum.mypage.dto.MypageDTO;
import com.github.yumyum.mypage.service.MypageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/myInfo/{id}")
    public ResponseEntity<?> getMyInfo(@PathVariable int id) {
        return ResponseEntity.ok(mypageService.getUserInfo(id));

    }

    // 내 정보에서 패스워드 수정하기
    @Operation(summary = "패스워드 수정")
    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody MypageDTO mypageDTO) {
        int updatedCount = mypageService.updatePassword(mypageDTO);
        if(updatedCount > 0) {
            return ResponseEntity.ok().body("비밀번호가 성공적으로 변경되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자를 찾을 수 없습니다.");
        }
    }
    // 탈퇴하기
    @Operation(summary = "회원탈퇴")
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        mypageService.deleteUser(id);
        return ResponseEntity.ok().body("회원탈퇴가 성공적으로 이루어졌습니다.");
        /*int deletedCount = mypageService.deleteUser(id);
        if(deletedCount > 0) {
            return ResponseEntity.ok().body("회원탈퇴가 성공적으로 이루어졌습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자를 찾을 수 없습니다.");
        }*/
    }
}
