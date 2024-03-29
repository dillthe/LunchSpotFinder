package com.github.yumyum.mypage.service;

import com.github.yumyum.exceptions.NotFoundException;
import com.github.yumyum.mypage.dto.UserDTO;
import com.github.yumyum.mypage.dto.UserMatchDTO;
import com.github.yumyum.mypage.repository.UserJpaRepository;
import com.github.yumyum.mypage.repository.UserMatchJpaRepository;
import com.github.yumyum.mypage.repository.entity.UserEntity;
import com.github.yumyum.mypage.repository.entity.UserMatchEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserMatchService {

    private final UserMatchJpaRepository userMatchJpaRepository;
    private final UserJpaRepository userRepository;

    public List<UserEntity> getUserIdSearch(UserDTO userDTO) {
        // 유저 아이디 검색 (user_id)
        return userRepository.findByUserIdContaining(userDTO.getUserId()).orElseThrow(() -> new NotFoundException("검색 결과가 없습니다."));
    }

    @Transactional
    public int insertUserMatch(UserMatchDTO userMatchDTO) {

        // 매칭 코드 W:대기, Y:수락, N:거절
        userMatchDTO.setMatchCode('W');

        System.out.println("userMatchDTO = " + userMatchDTO.getMatchCode());
        System.out.println("userMatchDTO = " + userMatchDTO.getSendUser());
        System.out.println("userMatchDTO = " + userMatchDTO.getReceiveUser());

        // DTO를 엔티티로 변환하는 과정 필요
        UserMatchEntity userMatchEntity = convertDtoToEntity(userMatchDTO);

        // 엔티티를 DB에 저장
        userMatchJpaRepository.save(userMatchEntity);

        return userMatchEntity.getMatchSn();

    }

    // 내가 받은 친구 목록 리스트
    @Transactional
    public List<UserMatchEntity> getReceiveMatchList(int receiveUserSn) {
        return userMatchJpaRepository.findByReceiveUser(receiveUserSn);


//        return userMatchJpaRepository.findByReceiveUser(userMatchDTO.getReceiveUser()).orElseThrow(() -> new NotFoundException("검색 결과가 없습니다."));
    }

    // 친구 수락/거절 Service
    public int satMatchStatus(UserMatchDTO userMatchDTO) {

        return 0;

    }

    // convertDtoToEntity
    private UserMatchEntity convertDtoToEntity(UserMatchDTO userMatchDTO) {
        UserMatchEntity userMatchEntity = new UserMatchEntity();
        userMatchEntity.setMatchCode(userMatchDTO.getMatchCode());
        userMatchEntity.setSendUser(userMatchDTO.getSendUser());
        userMatchEntity.setReceiveUser(userMatchDTO.getReceiveUser());
        return userMatchEntity;
    }


}
