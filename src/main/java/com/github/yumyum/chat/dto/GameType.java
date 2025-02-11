package com.github.yumyum.chat.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum GameType {

    TYPE1,
    TYPE2,
    TYPE3;

    @JsonCreator
    public static GameType fromGemeType(String value) {
        for (GameType gameType : GameType.values()) {
            if (gameType.name().equals(value)) {
                return gameType;
            }
        }
        return null;
    }
}
