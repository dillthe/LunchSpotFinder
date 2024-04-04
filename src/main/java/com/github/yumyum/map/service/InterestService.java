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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        } catch (RuntimeException exception) {
            throw new NotAcceptException("식당정보를 저장하는 도중 오류가 발생하였습니다.");
        }
            return interestEntityCreated.getInterestId();
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
