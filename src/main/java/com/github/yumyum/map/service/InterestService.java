package com.github.yumyum.map.service;


import com.github.yumyum.exceptions.NotAcceptException;
import com.github.yumyum.exceptions.NotFoundException;
import com.github.yumyum.map.repository.InterestRepository;
import com.github.yumyum.map.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
    private final InterestRepository interestRepository;

    //유저 정보 받아서 넣는거 나중에 로그인 완성되면 추가하기.
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
        Integer interestIdInt = Integer.valueOf(interestId);
        interestRepository.deleteById(interestIdInt);
    }
//
//    //관심있는 식당 삭제하기
//    public void deleteInterest(String memberId, String rstrId) {
//        try {
//            Integer memberIdInt = Integer.valueOf(memberId);
//            Integer rstrIdInt = Integer.valueOf(rstrId);
//            interestRepository.deleteByMemberIdAndRstrId(memberIdInt, rstrIdInt);
//        } catch (NumberFormatException e) {
//            throw new NotAcceptException("Id 형식이 올바르지 않습니다.");
//        }
//    }

//    public void deleteInterest(String interestId) {
//        try {
//            Integer interestIdInt = Integer.valueOf(interestId);
//            Optional<InterestEntity> interestEntityOptional = interestRepository.findById(interestIdInt);
//            if (interestEntityOptional.isPresent()) {
//                InterestEntity interestEntity = interestEntityOptional.get();
//                MemberEntity memberEntity = interestEntity.getMemberEntity();
//                RestaurantEntity restaurantEntity = interestEntity.getRestaurantEntity();
//                Integer memberId = memberEntity.getMemberId();
//                Integer rstrId = restaurantEntity.getRstrId();
//                // Now you have memberId and rstrId, you can use them as needed
//                // For example, you can pass them to another method or use them to perform additional operations
//
//                interestRepository.deleteById(interestIdInt);
//            } else {
//                throw new NotFoundException("Interest with ID " + interestId + " not found.");
//            }
//        } catch (NumberFormatException e) {
//            throw new NotAcceptException("InterestId 형식이 올바르지 않습니다.");
//        }
//    }

    //관심있는 식당 삭제하기
//    public void deleteInterest(Integer memberId, Integer rstrId) {
//        try {
//            Optional<MemberEntity> memberEntity = memberRepository.findById(memberId);
//            Optional<RestaurantEntity> restaurantEntity = restaurantJpaRepository.findById(rstrId);
//            if (memberEntity.isPresent() && restaurantEntity.isPresent()) {
//                Optional<InterestEntity> interest = interestRepository.findByMemberEntityAndRestaurantEntity(memberEntity, restaurantEntity);
//                if (interest.isPresent()) {
//                    interestRepository.deleteById(interest.get().getInterestId());
//                } else {
//                    throw new NotFoundException("해당하는 관심 식당 정보가 없습니다.");
//                }
//            } else {
//                throw new NotFoundException("해당하는 회원 정보 또는 식당 정보가 없습니다.");
//            }
//        } catch (NumberFormatException e) {
//            throw new NotAcceptException("Id 형식이 올바르지 않습니다.");
//        }
//    }

}
