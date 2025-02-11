package com.github.yumyum.map.web.controller;

import com.github.yumyum.common.util.RequestUtil;
import com.github.yumyum.map.repository.entity.InterestEntity;
import com.github.yumyum.map.service.InterestService;
import com.github.yumyum.map.web.dto.interested.InterestBody;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class InterestController implements ApiController{
    private final InterestService interestService;

    @Operation(summary = "관심있는 식당 정보 추가")
    @PostMapping("/interest/add")
    public ResponseEntity<String> addToInterest(@RequestBody InterestBody interestBody){
        interestBody.setMemberId(RequestUtil.getMemberId());
        interestService.addToInterest(interestBody);
        return ResponseEntity.ok("식당 정보가 관심식당에 추가되었습니다.");
    }

    @Operation(summary="관심있는 식당 정보 조회")
    @GetMapping("/interest/list")
    public ResponseEntity<List<InterestEntity>> getInterestList(){
        Integer memberId = RequestUtil.getMemberId();
        List<InterestEntity> interestEntityList = interestService.getInterestList(memberId);
        return ResponseEntity.ok(interestEntityList);
    }


    @Operation(summary = "관심있는 식당 정보 삭제")
    @DeleteMapping("/interest/delete/{interestId}")
    public ResponseEntity<String> deleteInterest(@PathVariable String interestId){
        interestService.deleteInterest(interestId);
        return ResponseEntity.ok("식당 정보가 관심식당에서 삭제되었습니다.");
    }

}
