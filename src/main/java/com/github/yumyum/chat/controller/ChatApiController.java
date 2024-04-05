package com.github.yumyum.chat.controller;

import com.github.yumyum.chat.dto.*;
import com.github.yumyum.chat.service.ChatApiService;
import com.github.yumyum.common.util.RequestUtil;
import com.github.yumyum.exceptions.InvalidFileException;
import com.github.yumyum.exceptions.InvalidParamException;
import com.github.yumyum.exceptions.InvalidValueException;
import com.github.yumyum.exceptions.NotFoundException;
import com.github.yumyum.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.beans.PropertyEditorSupport;
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
    @PostMapping(value = "/chatroom",
                consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity makeChatroom(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "채팅방 생성 Form Data", required = true,
            content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                    schema = @Schema(implementation = ChatroomDto.class)))
            ChatroomDto ChatroomDto) {
        log.info("ChatroomDto: {}", ChatroomDto);
        try {
            chatApiService.createChatroom(ChatroomDto);
        } catch (IOException e) {
            throw new InvalidParamException(e.toString());
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
    @PostMapping(value = "/chatroom/{chatroomId}",
                consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity updateChatroom(@PathVariable Integer chatroomId,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "채팅방 정보 변경 (제목, 이미지) Form Data", required = true,
                content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                        schema = @Schema(implementation = ChatroomUpdateDto.class)))
            ChatroomUpdateDto chatroomUpdateDto) {
        log.info("chatroomUpdateDto: {}", chatroomUpdateDto);
        try {
            chatApiService.updateChatroom(chatroomId, chatroomUpdateDto);
        } catch (IOException e) {
            throw new InvalidParamException(e.toString());
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
        if (chatApiService.leaveChatroomMember(leaveChatDto) == 0) {
            throw new NotFoundException(String.format("%s 유저는 %s 채팅방에 존재하지 않습니다.", leaveChatDto.getMemberId(), leaveChatDto.getChatroomId()));
        }
        return ResponseEntity.ok(String.format("%s 유저 %s 채팅방 나가기 성공", leaveChatDto.getMemberId(), leaveChatDto.getChatroomId()));
    }

    @Operation(summary = "채팅 저장")
    @PostMapping(value = "/chatroom/{chatroomId}/chat",
                consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity saveTextChat(@PathVariable Integer chatroomId,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "채팅방 채팅 저장 Form Data", required = true,
                content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                        schema = @Schema(implementation = ChatMessageDto.class)))
                ChatMessageDto chatMessageDto) {
        log.info("chatMessageDto: {}, chatroomId: {}", chatMessageDto, chatroomId);

        try {
            chatApiService.saveChatContent(chatMessageDto, chatroomId);
        } catch (IOException e) {
            throw new InvalidFileException("img 파일이 올바르지 않습니다.");
        }
        return ResponseEntity.ok(String.format("chatroomId(%s) 채팅 저장 성공 ", chatroomId));
    }

    /**
     * multipartfile null 변환
     * 참고링크: 출처: https://whitelife.tistory.com/246 [White Life Story:티스토리]
     *
     * @param binder
     * @throws Exception
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) throws Exception {
        binder.registerCustomEditor(MultipartFile.class, new PropertyEditorSupport() {

            @Override
            public void setAsText(String text) {
                log.debug("initBinder MultipartFile.class: {}; set null;", text);
                setValue(null);
            }

        });
    }

}
