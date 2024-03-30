package com.github.yumyum.map.web.controller;

import com.github.yumyum.common.util.RequestUtil;
import com.github.yumyum.map.service.InterestService;
import com.github.yumyum.map.web.dto.interested.InterestBody;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
//@RequestMapping("/rstr")
@Slf4j
public class InterestController implements ApiController{
    private final InterestService interestService;

    @Operation(summary = "관심있는 식당 정보 추가")
    @PostMapping("/add-to-interest")
    public ResponseEntity<String> addToInterest(@RequestBody InterestBody interestBody){
        interestBody.setMemberId(RequestUtil.getMemberId());
        interestService.addToInterest(interestBody);
        return ResponseEntity.ok("식당 정보가 즐겨찾기에 추가되었습니다.");
    }


    @Operation(summary = "관심있는 식당 정보 삭제")
    @DeleteMapping("/delete-interest/{interestId}")
    public ResponseEntity<String> deleteInterest(@PathVariable String interestId){
        interestService.deleteInterest(interestId);
        return ResponseEntity.ok("식당 정보가 즐겨찾기에서 삭제되었습니다.");
    }

}
