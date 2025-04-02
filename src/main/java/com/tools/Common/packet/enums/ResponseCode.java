package com.tools.Common.packet.enums;

public enum ResponseCode {
    PACKET(0x3031),
    JSON(0x3032);

    private final int value;

    ResponseCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ResponseCode fromValue(int value) {
        for (ResponseCode code : values()) {
            if (code.value == value) {
                return code;
            }
        }
        throw new IllegalArgumentException("Invalid type " + value);
    }
}
