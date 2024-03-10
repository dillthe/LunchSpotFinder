package com.github.yumyum.chat.controller;

import com.github.yumyum.chat.dto.ChatMessage;
import com.github.yumyum.exceptions.InvalidValueException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class ChatController {

    // "/chat.sendMessage"
    @MessageMapping("/chat/{roomId}/sendMessage")    // 구독 경로 설정
    @SendTo("/topic/{roomId}/public")    // 1:n 으로 메세지를 뿌릴 때 사용하는 구조, 보통 경로가 /topic 으로 시작
    public ChatMessage sendMessage(
            @Payload ChatMessage chatMessage
//            @PathVariable Integer roomId
    ) {
//        log.info("sendMessage roomId: {}", roomId);
        log.info("sendMessage chatMessage: {}", chatMessage);
        return chatMessage;
    }

    // "/chat.addUser"
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
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
//        headerAccessor.getSessionAttributes().put("roomId", roomId);
        log.info("addUser chatMessage: {}", chatMessage);
        log.info("addUser chatMessage getSender: {}", chatMessage.getSender());
        log.info("addUser chatMessage getRoomId: {}", chatMessage.getRoomId());
        log.info("addUser headerAccessor: {}", headerAccessor);

//        if (roomId != chatMessage.getRoomId()) {
//            throw new InvalidValueException("해당 채팅방 번호: " + roomId + " 는 유효하지 않습니다.");
//        }
        return chatMessage;
    }
}
