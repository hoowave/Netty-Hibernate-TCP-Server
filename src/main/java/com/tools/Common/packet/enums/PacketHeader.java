package com.tools.Common.packet.enums;

import com.tools.Common.exception.PacketException;

public enum PacketHeader {
    PING_REQUEST(0x3031),
    PING_RESPONSE(0x3032),
    LOGIN_REQUEST(0x3031),
    LOGIN_RESPONSE(0x3032);

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
