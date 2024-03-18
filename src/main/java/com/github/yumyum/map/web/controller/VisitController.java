package com.github.yumyum.map.web.controller;

import com.github.yumyum.map.service.VisitService;
import com.github.yumyum.map.web.dto.visited.VisitBody;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

//    @Operation(summary = "관심있는 식당 정보 삭제")
//    @DeleteMapping("/delete-interest/{interestId}")
//    public ResponseEntity<String> deleteInterest(@PathVariable String interestId){
//        interestService.deleteInterest(interestId);
//        return ResponseEntity.ok("식당 정보(ID:"+interestId+")가 즐겨찾기에서 삭제되었습니다.");
//    }
}
