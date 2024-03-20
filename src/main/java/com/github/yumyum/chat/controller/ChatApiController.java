package com.github.yumyum.chat.controller;

import com.github.yumyum.chat.dto.FriendshipId;
import com.github.yumyum.chat.entity.Member;
import com.github.yumyum.chat.service.ChatApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api")
public class ChatApiController {

    private final ChatApiService chatApiService;

    // TODO 모든 api 호출시 token값의 id와 userId 동일 여부 체크하도록 요청

    /**
     * 특정 유저와 친구관계인 유저 모두 불러오기
     * @param userId1
     * @return
     */
    @GetMapping(value = "/{userId}/friends")
    public List<Member> getMemberFriends(@PathVariable("userId") int userId1) {
        return chatApiService.getMemberAllFriends(userId1);
    }

    /**
     * 특정유저 2명 친구 관계 설정
     * @param userId1
     * @param friendshipId
     * @return
     */
    @PostMapping(value = "/{userId}/friend")
    public String makeFriendship(@PathVariable("userId") int userId1, @RequestBody FriendshipId friendshipId) {
        int friendShipSearchId = Optional.ofNullable(friendshipId.getFriendshipId())
                .orElseThrow(IllegalArgumentException::new);
        log.info("userId1: {}, friendShipSearchId: {}", userId1, friendShipSearchId);

        return chatApiService.checkMembersFriendShip(userId1, friendShipSearchId);
    }

    /**
     * 특정 유저 2명 친구 관계 제거
     * @param userId1
     * @param friendshipId
     * @return
     */
    @DeleteMapping(value = "/{userId}/friend")
    public String getMemberFriends(@PathVariable("userId") int userId1, @RequestBody FriendshipId friendshipId) {
        chatApiService.breakFreindship(userId1, friendshipId.getFriendshipId());

        return "delete 성공";
    }
}
