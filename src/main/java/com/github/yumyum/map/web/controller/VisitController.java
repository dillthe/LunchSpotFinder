package com.github.yumyum.map.web.controller;

import com.github.yumyum.map.service.VisitService;
import com.github.yumyum.map.web.dto.visited.VisitBody;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
public class VisitController implements ApiController{
    private final VisitService visitService;

    @Operation(summary = "방문한 식당 정보 추가")
    @PostMapping("/add-to-visit")
    public ResponseEntity<String> addToVisit(@RequestBody VisitBody visitBody){
        visitService.addToVisit(visitBody);
        return ResponseEntity.ok("방문한 식당 정보가 저장되었습니다.");
    }

    @Operation(summary = "방문 식당 정보 삭제")
    @DeleteMapping("/delete-visit/{visitId}")
    public ResponseEntity<String> deleteInterest(@PathVariable String visitId, @RequestBody Integer memberId){
        visitService.deleteVisit(visitId, memberId);
        return ResponseEntity.ok("유저아이디:" +memberId +"의 식당 정보(ID:"+visitId+")가 즐겨찾기에서 삭제되었습니다.");
    }

//    아래는 토큰정보로 불러오는건 아니고 내가 직접 바디를 넣어줘야함
//    @Operation(summary = "유저별 장바구니 조회하기 | json요청문:{usersId:7}" )
//    @PostMapping("/carts")
//    public List<CartItemDTO> getCartItemsByUsersId(@RequestBody Map<String, Integer> requestBody){
//        Integer usersId = requestBody.get("usersId");
//        return cartService.getCartItemsByUsersId(usersId);
//    }
}
