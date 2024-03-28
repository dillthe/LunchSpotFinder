package com.github.yumyum.chat.controller;

import com.github.yumyum.chat.dto.*;
import com.github.yumyum.chat.entity.Member;
import com.github.yumyum.chat.service.ChatApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api")
public class ChatApiController {

    private final ChatApiService chatApiService;

    // TODO 모든 api 호출시 token값의 id와 memberId 동일 여부 체크하도록 요청

    /**
     * 특정 유저와 친구관계인 유저 모두 불러오기
     * @param memberId
     * @return
     */
    @GetMapping(value = "/{memberId}/friends")
    public List<Member> getFriendshipMembers(@PathVariable int memberId) {
        return chatApiService.getMemberAllFriends(memberId);
    }

    /**
     * email or name keyword 포함하는 유저 검색
     * @param keyword
     * @return
     */
    @GetMapping
    public List<Member> searchMembers(@RequestParam("keyword") String keyword) {
        return chatApiService.searchMembers(keyword);
    }

    /**
     * 모든 유저 검색(훈희님이 메소드 생성 하셨으면 해당 내용으로 사용)
     * @param
     * @return
     */
    @GetMapping(value = "/members")
    public List<Member> getAllMembers() {
        return chatApiService.getAllMembers();
    }

    /**
     * 특정유저 2명 친구 관계 설정
     * @param memberId
     * @param friendshipId
     * @return
     */
    @PostMapping(value = "/{memberId}/friend")
    public String makeFriendship(@PathVariable int memberId, @RequestBody FriendshipId friendshipId) {
        int friendShipSearchId = Optional.ofNullable(friendshipId.getFriendshipId())
                .orElseThrow(IllegalArgumentException::new);
        log.info("memberId: {}, friendShipSearchId: {}", memberId, friendShipSearchId);

        return chatApiService.checkMembersFriendShip(memberId, friendShipSearchId);
    }

    /**
     * 특정 유저 2명 친구 관계 제거
     * @param memberId
     * @param friendshipId
     * @return
     */
    @DeleteMapping(value = "/{memberId}/friend")
    public String getFriendshipMembers(@PathVariable int memberId, @RequestBody FriendshipId friendshipId) {
        chatApiService.breakFreindship(memberId, friendshipId.getFriendshipId());

        return String.format("%s와 %s의 친구 관계 제거", memberId, friendshipId.getFriendshipId());
    }

    /**
     * 채팅방 생성
     * @param ChatroomDto
     * @return
     */
    @PostMapping(value = "/chatroom")
    public String makeChatroom(ChatroomDto ChatroomDto) {
        log.info("ChatroomDto: {}", ChatroomDto);
        try {
            chatApiService.createChatroom(ChatroomDto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "create chatroom 성공";
    }

    /**
     * 채팅방 정보 변경 (제목, 이미지)
     * @param chatroomId
     * @param chatroomUpdateDto
     * @return
     */
    @PostMapping(value = "/chatroom/{chatroomId}")
    public String updateChatroom(@PathVariable Integer chatroomId, ChatroomUpdateDto chatroomUpdateDto) {
        log.info("chatroomUpdateDto: {}", chatroomUpdateDto);
        try {
            chatApiService.updateChatroom(chatroomId, chatroomUpdateDto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "update chatroom 성공";
    }

    /**
     * 채팅방에 있는 모든 유저 조회
     * @param chatroomId
     * @return
     */
    @GetMapping(value = "/chatroom/{chatroomId}/members")
    public List<Member> getChatroomMembers(@PathVariable Integer chatroomId) {
        return chatApiService.getChatroomMembers(chatroomId);
    }

    /**
     * 특정 유저, 특정 채팅방 나기기
     * @param leaveChatDto
     * @return
     */
    @DeleteMapping(value = "/chatroom")
    public String leaveChatroomMember(@RequestBody LeaveChatDto leaveChatDto) {
        log.info("leaveChatDto: {}", leaveChatDto);
        chatApiService.leaveChatroomMember(leaveChatDto);
        return String.format("%s 유저 %s 채팅방 나가기 성공", leaveChatDto.getMemberId(), leaveChatDto.getChatroomId());
    }

}
