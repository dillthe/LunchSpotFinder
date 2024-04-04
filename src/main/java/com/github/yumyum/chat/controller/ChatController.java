package com.github.yumyum.chat.controller;

import com.github.yumyum.chat.dto.ChatMessage;
import com.github.yumyum.chat.service.ChatApiService;
import com.github.yumyum.common.util.RequestUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Controller
public class ChatController {

    private final ChatApiService chatApiService;


    // http://localhost:8085 : 채팅방 임시 link

    /**
     * 채팅 전송
     * @param chatMessage
     * @return
     */
    @Operation(summary = "채팅 전송")
    @MessageMapping("/chat/{roomId}/sendMessage")    // 구독 경로 설정 (메시지 라우팅)
    @SendTo("/topic/{roomId}/public")    // 1:n 으로 메세지를 뿌릴 때 사용하는 구조, 보통 경로가 /topic 으로 시작
    public ResponseEntity sendMessage(@Payload ChatMessage chatMessage) {  // 메시지의 Payload에 접근 (MessageConverter 의해서 변환)
        Integer memberId = RequestUtil.getMemberId();
        chatMessage.setMemberId(memberId);

        log.info("sendMessage chatMessage: {}", chatMessage);

        try {
            chatApiService.saveChatContent(chatMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok("sendmessage success");
    }


    /**
     * 채팅방 유저 참가
     * @param chatMessage
     * @param headerAccessor
     * @param roomId
     * @return
     * @link https://velog.io/@koseungbin/WebSocket
     */
    @Operation(summary = "채팅방 유저 참가")
    @MessageMapping("/chat/{roomId}/addUser")
    @SendTo("/topic/{roomId}/public")
    public ChatMessage addUser(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor,
            @PathVariable String roomId
    ) {
//        log.info("addUser roomId: {}", roomId); // {"sender":"aa","roomId":1,"type":"JOIN"}
//        log.info("addUser roomId: {}", roomId.get);

        // Add username in websocket session
        headerAccessor.getSessionAttributes().put("memberId", chatMessage.getMemberId());
//        headerAccessor.getSessionAttributes().put("roomId", roomId);
        log.info("addUser chatMessage: {}", chatMessage);
        log.info("addUser chatMessage memberId: {}", chatMessage.getMemberId());
        log.info("addUser chatMessage getRoomId: {}", chatMessage.getRoomId());
        log.info("addUser headerAccessor: {}", headerAccessor);

//        if (roomId != chatMessage.getRoomId()) {
//            throw new InvalidValueException("해당 채팅방 번호: " + roomId + " 는 유효하지 않습니다.");
//        }
        return chatMessage;
    }
}
