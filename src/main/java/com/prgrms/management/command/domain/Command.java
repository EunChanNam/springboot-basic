package com.prgrms.management.command.domain;

import com.prgrms.management.config.ErrorMessageType;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum Command {
    CREATE,
    LIST,
    BLACKLIST,
    CREATECUSTOMER,
    DELETECUSTOMER,
    LISTCUSTOMER,
    CREATEVOUCHER,
    DELETEVOUCHER,
    LISTVOUCHER,
    EXIT;

    public static Command of(String input) {
        return Arrays.stream(Command.values())
                .filter(e -> e.name().equals(input.toUpperCase()))
                .findAny()
                .orElseThrow(()-> new NoSuchElementException(Command.class+ ErrorMessageType.NOT_COMMAND_TYPE.getMessage()));
    }
}
