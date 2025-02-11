package com.github.yumyum.map.service;


import com.github.yumyum.common.util.RequestUtil;
import com.github.yumyum.exceptions.NotAcceptException;
import com.github.yumyum.exceptions.NotFoundException;
import com.github.yumyum.map.repository.InterestRepository;
import com.github.yumyum.map.repository.RestaurantRepository;
import com.github.yumyum.map.repository.entity.InterestEntity;
import com.github.yumyum.map.repository.entity.RestaurantEntity;
import com.github.yumyum.map.service.mapper.InterestMapper;
import com.github.yumyum.map.web.dto.interested.InterestBody;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TransientPropertyValueException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class InterestService {

    private final RestaurantRepository restaurantRepository;

    private final InterestRepository interestRepository;

    public Integer addToInterest(InterestBody interestBody) {
        RestaurantEntity restaurantEntity = restaurantRepository.findById(interestBody.getRstrId())
                .orElseThrow(() -> new NotFoundException("식당을 찾을 수 없습니다."));
        InterestEntity interestEntity = InterestMapper.INSTANCE.idAndInterestBodyToInterestEntity(null,interestBody);
        interestEntity.setRstrName(restaurantEntity.getRstrName());
        interestEntity.setAddress(restaurantEntity.getAddress());
        interestEntity.setTellNumber(restaurantEntity.getTellNumber());

       InterestEntity interestEntityCreated;
        try {
            interestEntityCreated = interestRepository.save(interestEntity);
        } catch (DataIntegrityViolationException e) {
            throw new NotAcceptException("관심 식당 리스트에 이미 저장되어 있습니다.");
        } catch (RuntimeException e) {
            throw new NotAcceptException("관심 식당을 저장하는 도중 예기치 않은 오류가 발생했습니다.");
        }
            return interestEntityCreated.getInterestId();
    }

    public List<InterestEntity> getInterestList(Integer memberId) {
        List<InterestEntity> interestEntityList = interestRepository.findByMemberId(memberId);
        return interestEntityList;
    }

    public void deleteInterest(String interestId) {
       try{
         Integer interestIdInt = Integer.valueOf(interestId);
         interestRepository.deleteById(interestIdInt);
       } catch (RuntimeException e) {
            throw new NotAcceptException("Id 형식이 올바르지 않습니다.");
       }
    }


}
