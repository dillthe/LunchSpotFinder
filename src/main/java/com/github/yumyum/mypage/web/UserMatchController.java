package com.github.yumyum.mypage.web;

import com.github.yumyum.mypage.dto.MatchDTO;
import com.github.yumyum.mypage.dto.MypageDTO;
import com.github.yumyum.mypage.dto.SearchMember;
import com.github.yumyum.mypage.dto.UserMatchDTO;
import com.github.yumyum.mypage.service.UserMatchService;
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
public class UserMatchController {

    private final UserMatchService userMatchService;


//    @Operation(summary = "매치 상태 확인")
//    @PostMapping("/matchState")
//    public ResponseEntity<?> matchState(@RequestBody MatchDTO matchDTO) {
//        return ResponseEntity.ok(userMatchService.matchState(matchDTO));
//    }

    @Operation(summary = "친구 id 검색 - 매칭상태와 상관없이 모두 검색")
    @PostMapping("/userIdSearch")
    public ResponseEntity<?> getUserIdSearch(@RequestBody String loginId) {
        return ResponseEntity.ok(userMatchService.getUserIdSearch(loginId));
    }

    @Operation(summary = "친구 신청 보내기 / sendUser: 보내는 사람, receiveUser: 받는 사람")
    @PostMapping("/insertUserMatch")
    public ResponseEntity<?> insertUserMatch(@RequestBody UserMatchDTO userMatchDTO) {
        int insertCount = userMatchService.insertUserMatch(userMatchDTO);

        if(insertCount == -1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("이미 친구신청을 보낸 상태입니다.");
        } else if(insertCount == -2) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("이미 친구인 상태입니다.");
        }

        return ResponseEntity.ok().body("친구신청이 성공적으로 이루어졌습니다.");

    }

    // 내가 받은 목록
    @Operation(summary = "내가 받은 목록 조회")
    @GetMapping("/getReceiveMatchList/{id}")
    public ResponseEntity<?> getReceiveMatchList(@PathVariable int id) {
        return ResponseEntity.ok(userMatchService.getReceiveMatchList(id));
    }

    // 친구 수락 / 거절 하기
    @Operation(summary = "친구 수락, 거절 API / matchSn: 매칭 시리얼 넘버, matchCode: Y(수락), N(거절)")
    @PostMapping("/setMatchStatus")
    public ResponseEntity<?> satMatchStatus(@RequestBody UserMatchDTO userMatchDTO) {
        return ResponseEntity.ok(userMatchService.setMatchStatus(userMatchDTO));
    }

    // 내 친구 목록
    @Operation(summary = "내 친구 목록 조회")
    @GetMapping("/getFriendList/{id}")
    public ResponseEntity<?> getFriendList(@PathVariable int id) {
        return ResponseEntity.ok(userMatchService.getFriendList(id));
    }

    // 친구 삭제(보류)
//    @Operation(summary = "친구 삭제 API / matchSn: 매칭 시리얼 넘버")
//    @PostMapping("/deleteMatch")
//    public ResponseEntity<?> deleteMatch(@RequestBody UserMatchDTO userMatchDTO) {
//        return ResponseEntity.ok(userMatchService.deleteMatch(userMatchDTO));
//    }

    // 내가 보낸 목록(보류)
}
