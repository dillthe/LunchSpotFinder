package com.github.yumyum.mypage.service;

import com.github.yumyum.exceptions.NotFoundException;
import com.github.yumyum.map.repository.RestaurantRepository;
import com.github.yumyum.map.repository.entity.RestaurantEntity;
import com.github.yumyum.member.entity.Member;
import com.github.yumyum.member.repository.MemberRepository;
import com.github.yumyum.mypage.dto.SavePlaceDTO;
import com.github.yumyum.mypage.dto.SaveReqDTO;
import com.github.yumyum.mypage.repository.SavePlaceRepository;
import com.github.yumyum.mypage.repository.entity.SavePlaceEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SavePlaceService {
    private final RestaurantRepository restaurantRepository;
    private final MemberRepository memberRepository;

    private final SavePlaceRepository savePlaceRepository;

    public List<SavePlaceEntity> getSavePlaceList(int id) {
        // 유저 즐겨찾기 목록 조회
        return savePlaceRepository.findByMemberId(id);
    }
    @Transactional
    public int addSavePlace(SaveReqDTO saveReqDTO) {
        // 즐겨찾기 추가
        // Member와 RestaurantEntity를 ID를 통해 조회
        Member member = memberRepository.findById(saveReqDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));
        RestaurantEntity restaurantEntity = restaurantRepository.findById(saveReqDTO.getRstrId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid restaurant ID"));

        // SavePlaceEntity 생성 및 저장
        SavePlaceEntity savePlaceEntity = SavePlaceEntity.builder()
                .member(member)
                .restaurant(restaurantEntity)
                .build();

        SavePlaceEntity savedEntity = savePlaceRepository.save(savePlaceEntity);

        // 저장된 엔티티의 ID 반환
        return savedEntity.getSaveSn();

    }

    public int delSavePlace(Integer saveSn) {
        // 즐겨찾기 해제
        // saveSn을 통해 SavePlaceEntity 조회
        SavePlaceEntity savePlaceEntity = savePlaceRepository.findById(saveSn).orElseThrow(() -> new NotFoundException("saveSn값이 존재하지 않습니다"));

        // SavePlaceEntity 삭제
        savePlaceRepository.delete(savePlaceEntity);

        return saveSn;
    }

    private SavePlaceEntity convertDtoToEntity(SavePlaceDTO savePlaceDTO) {
        // SavePlaceDTO를 SavePlaceEntity로 변환
        return SavePlaceEntity.builder()
                .saveSn(savePlaceDTO.getSaveSn())
                .build();
    }

}

