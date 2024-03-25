package com.github.yumyum.mypage.service;

import com.github.yumyum.exceptions.NotFoundException;
import com.github.yumyum.mypage.dto.SavePlaceDTO;
import com.github.yumyum.mypage.repository.SavePlaceRepository;
import com.github.yumyum.mypage.repository.entity.SavePlaceEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SavePlaceService {

    private final SavePlaceRepository savePlaceRepository;

    public List<SavePlaceEntity> getSavePlaceList(SavePlaceDTO savePlaceDTO) {
        // 유저 즐겨찾기 목록 조회
        return savePlaceRepository.findByUserSn(savePlaceDTO.getUserSn()).orElseThrow(() -> new NotFoundException("검색 결과가 없습니다."));
    }

    public int addSavePlace(SavePlaceDTO savePlaceDTO) {
        // 즐겨찾기 추가
        SavePlaceEntity savePlaceEntity = convertDtoToEntity(savePlaceDTO);

        savePlaceRepository.save(savePlaceEntity);

        return savePlaceEntity.getSaveSn();

    }

    public int delSavePlace(SavePlaceDTO savePlaceDTO) {
        // 즐겨찾기 해제
        SavePlaceEntity savePlaceEntity = convertDtoToEntity(savePlaceDTO);

        savePlaceRepository.delete(savePlaceEntity);

        return savePlaceEntity.getSaveSn();
    }

    private SavePlaceEntity convertDtoToEntity(SavePlaceDTO savePlaceDTO) {
        return SavePlaceEntity.builder()
                .saveSn(savePlaceDTO.getSaveSn())
                .userSn(savePlaceDTO.getUserSn())
                .placeSn(savePlaceDTO.getPlaceSn())
                .saveDt(savePlaceDTO.getSaveDt())
                .build();
    }
}

