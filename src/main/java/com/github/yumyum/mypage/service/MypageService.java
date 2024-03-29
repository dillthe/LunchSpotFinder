package com.github.yumyum.mypage.service;

import com.github.yumyum.exceptions.NotFoundException;
import com.github.yumyum.mypage.dto.UserDTO;
import com.github.yumyum.mypage.repository.UserJpaRepository;
import com.github.yumyum.mypage.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MypageService {
    
    private final UserJpaRepository userRepository;
    
    public UserEntity getUserInfo(int userSn) {
        return userRepository.findByUserSn(userSn)
                .orElseThrow(() -> new NotFoundException("마이페이지를 불러올 수 없습니다."));
    }

    public int updatePassword(UserDTO userDTO) {
        return userRepository.updatePassword(userDTO.getUserSn(), userDTO.getPassword());
    }

    public int deleteUser(int userSn) {
        return userRepository.deleteByUserSn(userSn);
    }
}
