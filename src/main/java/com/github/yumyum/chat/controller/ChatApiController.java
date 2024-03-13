package com.github.yumyum.chat.controller;

import com.github.yumyum.chat.dto.FriendshipId;
import com.github.yumyum.chat.entity.Friendship;
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

//    @GetMapping(value = "/{userId}/friends")
//    public List<Friendship> getMemberFriends(@PathVariable("userId") int userId1) {
//
//        // TODO token값의 id와 userId 동일 여부 체크
//
//        return chatApiService.getMemberAllFriends(userId1);
//    }

    @PostMapping(value = "/{userId}/friend")
    public String makeFriendship(@PathVariable("userId") int userId1, @RequestBody FriendshipId friendshipId) {

        // TODO token값의 id와 userId 동일 여부 체크

        int friendShipSearchId = Optional.ofNullable(friendshipId.getFriendshipId())
                .orElseThrow(IllegalArgumentException::new);
        log.info("userId1: {}, friendShipSearchId: {}", userId1, friendShipSearchId);

        return chatApiService.checkMembersFriendShip(userId1, friendShipSearchId);
    }

    @DeleteMapping(value = "/{userId}/friend")
    public String getMemberFriends(@PathVariable("userId") int userId1, @RequestBody FriendshipId friendshipId) {

        // TODO token값의 id와 userId 동일 여부 체크
        chatApiService.breakFreindship(userId1, friendshipId.getFriendshipId());

        return "delete 성공";
    }
}
