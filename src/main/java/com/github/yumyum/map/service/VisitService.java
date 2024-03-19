package com.github.yumyum.map.service;


import com.github.yumyum.exceptions.NotAcceptException;
import com.github.yumyum.exceptions.NotFoundException;
import com.github.yumyum.map.repository.RestaurantJpaRepository;
import com.github.yumyum.map.repository.VisitRepository;
import com.github.yumyum.map.repository.entity.RestaurantEntity;
import com.github.yumyum.map.repository.entity.VisitEntity;
import com.github.yumyum.map.service.mapper.VisitMapper;
import com.github.yumyum.map.web.dto.visited.VisitBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.time.LocalDate.now;

@RequiredArgsConstructor
@Slf4j
@Service
public class VisitService {

    private final RestaurantJpaRepository restaurantJpaRepository;
    private final VisitRepository visitRepository;

    //유저 정보 받아서 넣는거 나중에 로그인 완성되면 추가하기.
    public Integer addToVisit(VisitBody visitBody) {
        RestaurantEntity restaurantEntity = restaurantJpaRepository.findById(visitBody.getRstrId())
                .orElseThrow(() -> new NotFoundException("식당을 찾을 수 없습니다."));
        VisitEntity visitEntity = VisitMapper.INSTANCE.idAndVisitBodyToVisitEntity(null,visitBody);
        visitEntity.setRstrName(restaurantEntity.getRstrName());
        visitEntity.setAddress(restaurantEntity.getAddress());
        visitEntity.setTellNumber(restaurantEntity.getTellNumber());
        visitEntity.setVisitDate(now());
        VisitEntity visitEntityCreated;
        try {
            visitEntityCreated = visitRepository.save(visitEntity);
        } catch (RuntimeException exception) {
            throw new NotAcceptException("식당정보를 저장하는 도중 오류가 발생하였습니다.");
        }
            return visitEntityCreated.getVisitId();
    }

    //다녀간 식당 정보 삭제하기
    public void deleteVisit(String visitId, Integer memberId) {
        try {
            Integer visitIdInt = Integer.valueOf(visitId);
//            Integer memberIdInt = Integer.valueOf(memberId);
            visitRepository.findById(memberId);
            visitRepository.deleteById(visitIdInt);
        } catch (NumberFormatException e) {
            throw new NotAcceptException("Id 형식이 올바르지 않습니다.");
        }
    }


}
