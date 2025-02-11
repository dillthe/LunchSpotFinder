package com.github.yumyum.chat.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MessageType {

    CHAT_TEXT,
    CHAT_IMG,
    CHAT_GAME,
    JOIN,
    LEAVE;

    @JsonCreator
    public static MessageType fromMessageType(String value) {
        for (MessageType messageType : MessageType.values()) {
            if (messageType.name().equals(value)) {
                return messageType;
            }
        }
        return null;
    }
}
