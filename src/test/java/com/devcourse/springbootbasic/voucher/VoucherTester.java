package com.devcourse.springbootbasic.voucher;

import com.devcourse.springbootbasic.engine.exception.InvalidDataException;
import com.devcourse.springbootbasic.engine.io.InputConsole;
import com.devcourse.springbootbasic.engine.model.VoucherType;
import com.devcourse.springbootbasic.engine.voucher.domain.Voucher;
import com.devcourse.springbootbasic.engine.voucher.repository.MemoryVoucherRepository;
import com.devcourse.springbootbasic.engine.voucher.service.VoucherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.stream.Stream;

public class VoucherTester {

    public static Stream<Arguments> provideVoucherType() {
        return Stream.of(
                Arguments.of("1", VoucherType.FIXED_AMOUNT),
                Arguments.of("2", VoucherType.PERCENT_DISCOUNT)
        );
    }

    static Stream<Arguments> provideVoucher() {
        List<Voucher> list = List.of(
                VoucherType.FIXED_AMOUNT.getVoucherFactory().create(10),
                VoucherType.PERCENT_DISCOUNT.getVoucherFactory().create(5)
        );
        return Stream.of(
                Arguments.of(list.get(0), list.get(0)),
                Arguments.of(list.get(1), list.get(1))
        );
    }

    @ParameterizedTest
    @MethodSource("provideVoucherType")
    void voucherTypeTest(String input, VoucherType expected) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InputConsole inputConsole = new InputConsole();
        VoucherType voucherType = inputConsole.inputVoucherType();
        Assertions.assertEquals(expected, voucherType);
    }

    @ParameterizedTest
    @ValueSource(strings = {"4", "-1"})
    void voucherTypeExceptionTest(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InputConsole inputConsole = new InputConsole();
        Assertions.assertThrows(InvalidDataException.class, inputConsole::inputVoucherType);
    }

    @ParameterizedTest
    @MethodSource("provideVoucher")
    void voucherCreationTest(Voucher input, Voucher expect) {
        VoucherService voucherService = new VoucherService(new MemoryVoucherRepository());
        Voucher result = voucherService.createVoucher(input);
        Assertions.assertEquals(expect, result);
    }

}
