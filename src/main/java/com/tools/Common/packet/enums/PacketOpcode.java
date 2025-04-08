package com.tools.Common.packet.enums;

import com.tools.Common.exception.PacketException;

public enum PacketOpcode {
    SUCCESS(0x4631),
    FAIL(0x4632),

    PING(0x3031),

    LOGIN(0x3032),

    CHARACTER(0x3033),
    ;

    private final int value;

    PacketOpcode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PacketOpcode fromValue(int value) {
        for (PacketOpcode opcode : values()) {
            if (opcode.value == value) {
                return opcode;
            }
        }
        throw new PacketException("Unknown Opcode: " + value);
    }

}
