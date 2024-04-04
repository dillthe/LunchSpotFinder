package com.github.yumyum.chat.controller;

import com.github.yumyum.chat.dto.*;
import com.github.yumyum.chat.service.ChatApiService;
import com.github.yumyum.common.util.RequestUtil;
import com.github.yumyum.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api")
public class ChatApiController {

    private final ChatApiService chatApiService;

    /**
     * 로그인 유저와 친구관계인 유저 모두 불러오기
     *
     * @return
     */
    @Operation(summary = "로그인 유저와 친구관계인 유저 모두 불러오기")
    @GetMapping(value = "/friends")
    public List<MemberDto> getFriendshipMembers() {
        Integer memberId = RequestUtil.getMemberId();
        log.info("memberId: {}", memberId);
        return chatApiService.getMemberAllFriends(memberId);
    }

    /**
     * email or name keyword 포함하는 유저 검색
     *
     * @param keyword
     * @return
     */
    @Operation(summary = "email or name keyword 포함하는 유저 검색")
    @GetMapping
    public List<MemberDto> searchMembers(@RequestParam("keyword") String keyword) {
        return chatApiService.searchMembers(keyword);
    }

    /**
     * 모든 유저 검색(훈희님이 메소드 생성 하셨으면 해당 내용으로 사용)
     *
     * @param
     * @return
     */
    @Operation(summary = "모든 유저 불러오기")
    @GetMapping(value = "/members")
    public List<MemberDto> getAllMembers() {
        return chatApiService.getAllMembers();
    }

    /**
     * 특정유저 2명 친구 관계 설정
     *
     * @param memberId
     * @param friendshipId
     * @return
     */
    @Operation(summary = "특정유저 2명 친구 관계 설정")
    @PostMapping(value = "/{memberId}/friend")
    public ResponseEntity makeFriendship(@PathVariable int memberId, @RequestBody FriendshipId friendshipId) {
        int friendShipSearchId = Optional.ofNullable(friendshipId.getFriendshipId())
                .orElseThrow(IllegalArgumentException::new);
        log.info("memberId: {}, friendShipSearchId: {}", memberId, friendShipSearchId);

        return ResponseEntity.ok(chatApiService.checkMembersFriendShip(memberId, friendShipSearchId));
    }

    /**
     * 특정 유저 2명 친구 관계 제거
     *
     * @param memberId
     * @param friendshipId
     * @return
     */
    @Operation(summary = "특정유저 2명 친구 관계 제거")
    @DeleteMapping(value = "/{memberId}/friend")
    public ResponseEntity getFriendshipMembers(@PathVariable int memberId, @RequestBody FriendshipId friendshipId) {
        chatApiService.breakFreindship(memberId, friendshipId.getFriendshipId());

        return ResponseEntity.ok(String.format("%s와 %s의 친구 관계 제거", memberId, friendshipId.getFriendshipId()));
    }

    /**
     * 채팅방 생성
     *
     * @param ChatroomDto
     * @return
     */
    @Operation(summary = "채팅방 생성")
    @PostMapping(value = "/chatroom")
    public ResponseEntity makeChatroom(ChatroomDto ChatroomDto) {
        log.info("ChatroomDto: {}", ChatroomDto);
        try {
            chatApiService.createChatroom(ChatroomDto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("create chatroom 성공");
    }

    /**
     * 채팅방 정보 변경 (제목, 이미지)
     *
     * @param chatroomId
     * @param chatroomUpdateDto
     * @return
     */
    @Operation(summary = "채팅방 정보 변경 (제목, 이미지)")
    @PostMapping(value = "/chatroom/{chatroomId}")
    public ResponseEntity updateChatroom(@PathVariable Integer chatroomId, ChatroomUpdateDto chatroomUpdateDto) {
        log.info("chatroomUpdateDto: {}", chatroomUpdateDto);
        try {
            chatApiService.updateChatroom(chatroomId, chatroomUpdateDto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("update chatroom 성공");
    }

    /**
     * 채팅방에 있는 모든 유저 조회
     *
     * @param chatroomId
     * @return
     */
    @Operation(summary = "채팅방에 있는 모든 유저 조회")
    @GetMapping(value = "/chatroom/{chatroomId}/members")
    public List<MemberDto> getChatroomMembers(@PathVariable Integer chatroomId) {
        return chatApiService.getChatroomMembers(chatroomId);
    }

    /**
     * 특정 유저, 특정 채팅방 나기기
     *
     * @param leaveChatDto
     * @return
     */
    @Operation(summary = "특정 유저, 특정 채팅방 나기기")
    @DeleteMapping(value = "/chatroom")
    public ResponseEntity leaveChatroomMember(@RequestBody LeaveChatDto leaveChatDto) {
        log.info("leaveChatDto: {}", leaveChatDto);
        chatApiService.leaveChatroomMember(leaveChatDto);
        return ResponseEntity.ok(String.format("%s 유저 %s 채팅방 나가기 성공", leaveChatDto.getMemberId(), leaveChatDto.getChatroomId()));
    }

    @Operation(summary = "채팅 저장 (문자)")
    @PostMapping(value = "/chatroom/{chatroomId}/chat", params = "type=text")
    public ResponseEntity saveTextChat(ChatTextMessage chatTextMessage,
                                       @PathVariable Integer chatroomId) {
        log.info("chatTextMessage: {}", chatTextMessage);
        chatApiService.saveChatTextContent(chatTextMessage);
        return ResponseEntity.ok(String.format("chatroomId(%s) 채팅(%s) 저장 성공 ", chatroomId, chatTextMessage));
    }

    @Operation(summary = "채팅 저장 (이미지)")
    @PostMapping(value = "/chatroom/{chatroomId}/chat", params = "type=img")
    public ResponseEntity saveTextChat(ChatImgMessage chatImgMessage,
                                       @PathVariable Integer chatroomId) {
        try {
            chatApiService.saveChatImgContent(chatImgMessage);
        } catch (IOException e) {
            throw new RuntimeException("img 파일이 올바르지 않습니다.");
        }
        return ResponseEntity.ok(String.format("chatroomId(%s) 이미지 저장 성공 ", chatroomId));
    }
}
