package org.prgrms.kdt.io;

import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

import static org.apache.commons.lang3.math.NumberUtils.toLong;

public class InputConsole implements Input {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String input() {
        return scanner.nextLine();
    }

    @Override
    public long inputLong() {
        return toLong(scanner.nextLine());
    }

    @Override
    public Optional<UUID> inputUUID() {
        try {
            return Optional.of(UUID.fromString(scanner.nextLine()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
