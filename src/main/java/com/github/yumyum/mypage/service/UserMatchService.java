package com.github.yumyum.mypage.service;

import com.github.yumyum.chat.entity.Friendship;
import com.github.yumyum.chat.repository.FriendshipRepository;
import com.github.yumyum.chat.service.ChatApiService;
import com.github.yumyum.member.entity.Member;
import com.github.yumyum.member.repository.MemberRepository;
import com.github.yumyum.mypage.dto.UserMatchDTO;
import com.github.yumyum.mypage.repository.MypageRepository;
import com.github.yumyum.mypage.repository.UserMatchRepository;
import com.github.yumyum.mypage.repository.entity.UserMatchEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserMatchService {

    private final UserMatchRepository userMatchRepository;
    private final MemberRepository memberRepository;
    private final FriendshipRepository friendshipRepository;
    private final ChatApiService chatApiService;

//    public List<UserMatchEntity> matchState(MatchDTO matchDTO) {
//        return userMatchJpaRepository.matchState(matchDTO.getId(), matchDTO.getMatchId());
//    }

    public List<Member> getUserIdSearch(String loginId) {

        return memberRepository.getUserIdSearch(loginId);
    }

    @Transactional
    public int insertUserMatch(UserMatchDTO userMatchDTO) {

        // 현재 매칭 상태 확인
        UserMatchEntity userMatchEntities = userMatchRepository.matchState(userMatchDTO.getSendUser(), userMatchDTO.getReceiveUser());
        if(userMatchEntities != null) {
            switch (userMatchEntities.getMatchCode()) {
                case 'W': // 대기 상태인 경우
                    return -1;
                case 'Y': // 수락 상태인 경우
                    return -2;
                default:
                    break;
            }
        }

        Member sendUser = memberRepository.findById(userMatchDTO.getSendUser()).orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));
        Member receiveUser = memberRepository.findById(userMatchDTO.getReceiveUser()).orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        // 엔티티 생성
        UserMatchEntity userMatchEntity = UserMatchEntity.builder()
                .matchCode('W') // 매칭 코드 W:대기, Y:수락, N:거절
                .sendUser(sendUser)
                .receiveUser(receiveUser)
                .build();

        // 엔티티를 DB에 저장
        userMatchRepository.save(userMatchEntity);

        return userMatchEntity.getMatchSn();

    }

    // 내가 받은 친구 목록 리스트
    @Transactional
    public List<UserMatchEntity> getReceiveMatchList(int id) {
        return userMatchRepository.findByReceiveUser(id);
    }

    // 친구 수락/거절
    @Transactional
    public int setMatchStatus(UserMatchDTO userMatchDTO) {
        UserMatchEntity userMatch = userMatchRepository.findById(userMatchDTO.getMatchSn()).orElseThrow(() -> new EntityNotFoundException("Match not found"));
        char matchCode = userMatchDTO.getMatchCode();
        if(matchCode == 'Y') {
            chatApiService.makeFriend(userMatch.getSendUser().getId(), userMatch.getReceiveUser().getId());
        }
        userMatch.setMatchCode(matchCode);
        return userMatchRepository.save(userMatch).getMatchSn();
//        return userMatchRepository.updateMatchCode(userMatchDTO.getMatchSn(), userMatchDTO.getMatchCode());
    }

    // 내 친구 목록 조회
    @Transactional
    public List<Member> getFriendList(int id) {
        List<Friendship> friendships = friendshipRepository.getFriendList(id);
        if(friendships.isEmpty()) {
            throw new EntityNotFoundException("Friend not found");
        }

        List<Member> friendList = new ArrayList<>();
        for(Friendship f : friendships) {
            int friendId = f.getMember2().getId();
            Member member = memberRepository.findById(friendId).orElseThrow(() -> new EntityNotFoundException("Friend not found"));
            friendList.add(member);
        }
        return friendList;
    }

//
//    // convertDtoToEntity
//    private UserMatchEntity convertDtoToEntity(UserMatchDTO userMatchDTO) {
//        UserMatchEntity userMatchEntity = new UserMatchEntity();
//        userMatchEntity.setMatchCode(userMatchDTO.getMatchCode());
//        userMatchEntity.setSendUser(userMatchDTO.getSendUser());
//        userMatchEntity.setReceiveUser(userMatchDTO.getReceiveUser());
//        return userMatchEntity;
//    }


}
