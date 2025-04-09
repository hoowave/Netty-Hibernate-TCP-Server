package com.tools.Common.packet.enums;

import com.tools.Common.exception.PacketException;

public enum PacketHeader {
    PING_REQUEST(0x3131),
    PING_RESPONSE(0x3132),

    LOGIN_REQUEST(0x3231),
    LOGIN_RESPONSE(0x3232),

    CHARACTER_LIST_REQUEST(0x3331),
    CHARACTER_LIST_RESPONSE(0x3332),
    CHARACTER_INFO_REQUEST(0x3333),
    CHARACTER_INFO_RESPONSE(0x3334),
    CHARACTER_ADD_REQUEST(0x3335),
    CHARACTER_ADD_RESPONSE(0x3336),
    CHARACTER_REMOVE_REQUEST(0x3337),
    CHARACTER_REMOVE_RESPONSE(0x3338),
    CHARACTER_SELECT_REQUEST(0x3339),
    CHARACTER_SELECT_RESPONSE(0x334A),

    GAME_PLAYER_MOVE_REQUEST(0x3430),
    GAME_PLAYER_EXP_REQUEST(0x3431),
    ;

    private final int value;

    PacketHeader(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PacketHeader fromValue(int value) {
        for (PacketHeader header : values()) {
            if (header.value == value) {
                return header;
            }
        }
        throw new PacketException("Unknown Header: " + value);
    }
}
