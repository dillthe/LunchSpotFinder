package com.github.yumyum.chat.config;

import com.github.yumyum.chat.dto.ChatMessage;
import com.github.yumyum.chat.dto.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageTemplate;

    @EventListener
    public void handleWebSocketDisconnectListener(
            SessionDisconnectEvent event
    ) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Integer userId = (Integer) headerAccessor.getSessionAttributes().get("userId");
        Integer roomId = (Integer) headerAccessor.getSessionAttributes().get("roomId");
        log.info("roomId: {}", roomId);
        log.info("headerAccessor.getSessionAttributes(): {}", headerAccessor.getSessionAttributes());
        if (userId != null) {
            log.info("Room({}) User disconnected: {}", roomId, userId);
            ChatMessage chatMessage = ChatMessage.builder()
                    .memberId(userId)
                    .type(MessageType.LEAVE)
                    .build();
            messageTemplate.convertAndSend("/topic/${roomId}/public", chatMessage);

        }
    }
}
