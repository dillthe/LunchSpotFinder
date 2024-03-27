package com.github.yumyum.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableWebSocketMessageBroker
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // MessageBroker 등록
        // topic: 한명이 message 를 발행시, 해당 토픽을 구독하고 있는 n명에게 메세지 전송
        // queue: 한명이 message 를 발행시, 발행한 한 명에게 다시 정보 전송
        registry.enableSimpleBroker("/topic");

        // 도착경로에 대한 prefix 를 설정
        // ex> /topic/hello 라는 토픽에 대해 구독을 신청했을 때 실제 경로는 /app/topic/hello
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 연결할 소켓 엔드포인트를 지정
        // TODO: HTTPS
        registry.addEndpoint("/ws").withSockJS();
    }
}
