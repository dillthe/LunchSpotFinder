package com.github.yumyum.map.web.controller;

import com.github.yumyum.common.util.RequestUtil;
import com.github.yumyum.map.repository.entity.InterestEntity;
import com.github.yumyum.map.repository.entity.VisitEntity;
import com.github.yumyum.map.service.VisitService;
import com.github.yumyum.map.web.dto.visited.VisitBody;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class VisitController implements ApiController{
    private final VisitService visitService;

    @Operation(summary = "방문한 식당 정보 추가")
    @PostMapping("/visit/add")
    public ResponseEntity<String> addToVisit(@RequestBody VisitBody visitBody){
        visitBody.setMemberId(RequestUtil.getMemberId());
        visitService.addToVisit(visitBody);
        return ResponseEntity.ok("방문한 식당 정보가 저장되었습니다.");
    }

    @Operation(summary="방문 식당 정보 조회")
    @GetMapping("/visit/list")
    public ResponseEntity<List<VisitEntity>> getVisitList(){
        Integer memberId = RequestUtil.getMemberId();
        List<VisitEntity> visitEntityList = visitService.getVisitList(memberId);
        return ResponseEntity.ok(visitEntityList);
    }

    @Operation(summary = "방문 식당 정보 삭제")
    @DeleteMapping("/visit/delete/{visitId}")
    public ResponseEntity<String> deleteInterest(@PathVariable String visitId){
        visitService.deleteVisit(visitId);
        return ResponseEntity.ok("식당 정보(ID:"+visitId+")가 방문한 식당 리스트에서 삭제되었습니다.");
    }
}
