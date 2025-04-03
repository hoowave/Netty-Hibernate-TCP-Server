package com.tools.Common.packet.enums;

import com.tools.Common.exception.PacketException;

public enum PacketHeader {
    PING_REQUEST(0x3031),
    PING_RESPONSE(0x3032),

    LOGIN_REQUEST(0x3031),
    LOGIN_RESPONSE(0x3032),

    CHARACTER_LIST_REQUEST(0x3031),
    CHARACTER_LIST_RESPONSE(0x3032),
    CHARACTER_ADD_REQUEST(0x3033),
    CHARACTER_ADD_RESPONSE(0x3034),
    CHARACTER_REMOVE_REQUEST(0x3035),
    CHARACTER_REMOVE_RESPONSE(0x3036),
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
