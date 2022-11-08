package org.prgrms.springorder.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.prgrms.springorder.domain.VoucherType;
import org.prgrms.springorder.request.VoucherCreateRequest;

@TestInstance(Lifecycle.PER_CLASS)
class ConsoleTest {

    @DisplayName("inputStringToLong 테스트 - 입력을 받아 입력값을 long 으로  리턴한다.")
    @Test
    void inputStringToLongTest() {
        //given
        String userInput = "10";
        InputStream inputStream = generateUserInput(userInput);
        System.setIn(inputStream);

        Input consoleInput = new ConsoleInput(
            new BufferedReader(new InputStreamReader(inputStream)));
        Output mockOutput = mock(Output.class);
        Console console = new Console(consoleInput, mockOutput);

        //when
        long inputStringToLong = console.inputStringToLong();
        //then
        assertEquals(10L, inputStringToLong);

    }

    @DisplayName("inputStringToLong 예외 테스트 - 입력을 받아 입력값이 long이 아니면 예외를 던진다. ")
    @Test
    void inputStringToLongFailTestThrowException() {
        //given
        String userInput = "not Long";
        InputStream inputStream = generateUserInput(userInput);
        System.setIn(inputStream);

        Input consoleInput = new ConsoleInput(
            new BufferedReader(new InputStreamReader(inputStream)));
        Output mockOutput = mock(Output.class);
        Console console = new Console(consoleInput, mockOutput);
        //when & then
        assertThrows(IllegalArgumentException.class, console::inputStringToLong);
    }

    @DisplayName("createVoucherRequest 테스트 - 값을 받아 VoucherCreateRequest 를 만든다.")
    @ParameterizedTest
    @CsvSource(value = {"fixed, 100", "percent, 50"}, delimiterString = ",")
    void createRequestTest(String voucherTypeInput, Long discountAmount) {
        //given
        InputStream inputStream = createInputStreamSequence(voucherTypeInput,
            discountAmount.toString());
        System.setIn(inputStream);
        Input consoleInput = new ConsoleInput(
            new BufferedReader(new InputStreamReader(inputStream)));

        Output mockConsoleOutput = mock(Output.class);
        Console console = new Console(consoleInput, mockConsoleOutput);

        //when
        VoucherCreateRequest voucherCreateRequest = console.getVoucherCreateRequest();

        //then
        assertEquals(VoucherType.of(voucherTypeInput), voucherCreateRequest.getVoucherType());
        assertEquals(discountAmount, voucherCreateRequest.getDiscountAmount());
    }

    @DisplayName("showMessage 테스트 - 파라미터로 넘어온 메시지 여러건을 건 마다 개행문자와 함께 출력한다.")
    @Test
    void showMessagesListTest() {
        //given
        List<String> messages = List.of("message1", "message2", "message3");

        Input mockConsoleInput = mock(Input.class);
        Output mockConsoleOutput = mock(Output.class);
        Console console = new Console(mockConsoleInput, mockConsoleOutput);

        doNothing().when(mockConsoleOutput)
            .showMessages(messages.toArray(String[]::new));

        //when
        console.showMessages(messages);
        //then
        verify(mockConsoleOutput).showMessages(messages.toArray(String[]::new));
    }

    private InputStream generateUserInput(final String input) {
        return new ByteArrayInputStream(input.getBytes());
    }

    private InputStream createInputStreamSequence(String... inputs) {
        List<InputStream> inputStreams = Arrays.stream(inputs)
            .map(input -> this.generateUserInput(input + "\n"))
            .collect(Collectors.toList());

        return new SequenceInputStream(Collections.enumeration(inputStreams));
    }

}