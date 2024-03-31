package com.github.yumyum.mypage.service;

import com.github.yumyum.exceptions.NotAcceptException;
import com.github.yumyum.exceptions.NotFoundException;
import com.github.yumyum.member.entity.Member;
import com.github.yumyum.mypage.dto.MypageDTO;
import com.github.yumyum.mypage.repository.MypageRepository;
import com.github.yumyum.mypage.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MypageService {
    
    private final MypageRepository mypageRepository;
    
    public Member getUserInfo(int id) {
        return mypageRepository.findById(id).orElseThrow(() -> new NotFoundException("마이페이지를 불러올 수 없습니다."));
    }

    public int updatePassword(MypageDTO mypageDTO) {
        return mypageRepository.updatePassword(mypageDTO.getId(), mypageDTO.getPassword());
    }

    public void deleteUser(int id) {
        try {
            mypageRepository.deleteById(id);
        } catch (NumberFormatException e) {
            throw new NotAcceptException("회원탈퇴 Id 형식이 올바르지 않습니다.");
        }
    }
}
