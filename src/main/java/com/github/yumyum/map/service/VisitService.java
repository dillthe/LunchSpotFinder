package com.github.yumyum.map.service;


import com.github.yumyum.exceptions.NotAcceptException;
import com.github.yumyum.exceptions.NotFoundException;
import com.github.yumyum.map.repository.RestaurantRepository;
import com.github.yumyum.map.repository.VisitRepository;
import com.github.yumyum.map.repository.entity.InterestEntity;
import com.github.yumyum.map.repository.entity.RestaurantEntity;
import com.github.yumyum.map.repository.entity.VisitEntity;
import com.github.yumyum.map.service.mapper.VisitMapper;
import com.github.yumyum.map.web.dto.visited.VisitBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TransientPropertyValueException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.time.LocalDate.now;

@RequiredArgsConstructor
@Slf4j
@Service
public class VisitService {

    private final RestaurantRepository restaurantRepository;
    private final VisitRepository visitRepository;

    //다녀간 식당 정보 추가하기
    public Integer addToVisit(VisitBody visitBody) {
        RestaurantEntity restaurantEntity = restaurantRepository.findById(visitBody.getRstrId())
                .orElseThrow(() -> new NotFoundException("식당을 찾을 수 없습니다."));
        VisitEntity visitEntity = VisitMapper.INSTANCE.idAndVisitBodyToVisitEntity(null,visitBody);
        visitEntity.setRstrName(restaurantEntity.getRstrName());
        visitEntity.setAddress(restaurantEntity.getAddress());
        visitEntity.setTellNumber(restaurantEntity.getTellNumber());
        visitEntity.setVisitDate(now());
        VisitEntity visitEntityCreated;

        try {
            visitEntityCreated = visitRepository.save(visitEntity);
        }  catch (RuntimeException e) {
            throw new NotAcceptException("다녀간 식당 정보를 저장하는 도중 예기치 않은 오류가 발생했습니다.");
        }
            return visitEntityCreated.getVisitId();
    }

    //다녀간 식당 정보 조회하기
    public List<VisitEntity> getVisitList(Integer memberId) {
        List<VisitEntity> interestEntityList = visitRepository.findByMemberId(memberId);
        return interestEntityList;
    }

    //다녀간 식당 정보 삭제하기
    public void deleteVisit(String visitId) {
        try {
            Integer visitIdInt = Integer.valueOf(visitId);
            visitRepository.deleteById(visitIdInt);
        } catch (RuntimeException e) {
            throw new NotAcceptException("Id 형식이 올바르지 않습니다.");
        }
    }


}
