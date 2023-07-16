package com.tengerine.voucher_system.application.voucher.controller;

import com.tengerine.voucher_system.application.voucher.model.DiscountValue;
import com.tengerine.voucher_system.application.voucher.model.Voucher;
import com.tengerine.voucher_system.application.voucher.model.VoucherType;
import com.tengerine.voucher_system.application.voucher.service.VoucherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringJUnitConfig
class VoucherControllerTest {

    static List<VoucherDto> voucherDto = List.of(
            new VoucherDto(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 23), LocalDateTime.now(), Optional.empty()),
            new VoucherDto(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, 41), LocalDateTime.now(), Optional.empty()),
            new VoucherDto(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 711), LocalDateTime.now(), Optional.empty())
    );
    static List<Voucher> vouchers = voucherDto.stream()
            .map(VoucherDto::to)
            .toList();
    VoucherController controller;
    VoucherService service;

    static Stream<Arguments> provideVoucherDto() {
        return voucherDto.stream()
                .map(Arguments::of);
    }

    @BeforeEach
    void init() {
        service = mock(VoucherService.class);
        controller = new VoucherController(service);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 바우처를 생성 시 성공한다.")
    @MethodSource("provideVoucherDto")
    void createVoucher_ParamNotExistVoucherDto_RegisterAndReturnVoucherDto(VoucherDto voucherDto) {
        given(service.createVoucher(any())).willReturn(voucherDto.to());

        VoucherDto createdVoucher = controller.createVoucher(voucherDto);

        assertThat(createdVoucher.voucherId()).isEqualTo(voucherDto.voucherId());
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 갱신 시 성공한다.")
    @MethodSource("provideVoucherDto")
    void updateVoucher_ParamExistVoucherDto_UpdateAndReturnVoucherDto(VoucherDto voucherDto) {
        given(service.updateVoucher(any())).willReturn(voucherDto.to());

        VoucherDto updatedVoucher = controller.updateVoucher(voucherDto);

        assertThat(updatedVoucher.voucherId()).isEqualTo(voucherDto.voucherId());
    }

    @Test
    @DisplayName("모든 바우처 디티오를 리스트로 반환한다.")
    void voucherList_ParamVoid_ReturnVoucherList() {
        given(service.findVouchers()).willReturn(vouchers);

        List<VoucherDto> result = controller.voucherList();

        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 아이디로 찾으면 성공한다.")
    @MethodSource("provideVoucherDto")
    void voucherById_ParamExistVoucherId_ReturnVoucherDto(VoucherDto voucherDto) {
        given(service.findVoucherById(any())).willReturn(voucherDto.to());

        VoucherDto foundVoucherDto = controller.voucherById(voucherDto.voucherId());

        assertThat(foundVoucherDto.voucherId()).isEqualTo(voucherDto.voucherId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 바우처를 아이디로 찾으면 실패한다.")
    @MethodSource("provideVoucherDto")
    void voucherId_ParamNotExistVoucherId_Exception(VoucherDto voucherDto) {
        Exception exception = catchException(() -> controller.voucherById(voucherDto.voucherId()));

        assertThat(exception).isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 바우처 타입으로 찾으면 성공한다.")
    @MethodSource("provideVoucherDto")
    void voucherByType_ParamExistVoucherId_ReturnVoucherDto(VoucherDto voucherDto) {
        given(service.findVoucherByVoucherType(any())).willReturn(voucherDto.to());

        VoucherDto foundVoucherDto = controller.voucherByType(voucherDto.voucherType());

        assertThat(foundVoucherDto.voucherId()).isEqualTo(voucherDto.voucherId());
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 생성일자로 찾으면 성공한다.")
    @MethodSource("provideVoucherDto")
    void voucherByCreatedAt_ParamExistVoucherId_ReturnVoucherDto(VoucherDto voucherDto) {
        given(service.findVoucherByCreatedAt(any())).willReturn(voucherDto.to());

        VoucherDto foundVoucherDto = controller.voucherByCreatedAt(voucherDto.createdAt());

        assertThat(foundVoucherDto.voucherId()).isEqualTo(voucherDto.voucherId());
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 제거하면 성공한다.")
    @MethodSource("provideVoucherDto")
    void deleteVoucherById_ParamExistVoucherId_DeleteAndReturnVoucherDto(VoucherDto voucherDto) {
        given(service.deleteVoucherById(any())).willReturn(voucherDto.to());

        VoucherDto deletedVoucherDto = controller.deleteVoucherById(voucherDto.voucherId());

        assertThat(deletedVoucherDto.voucherId()).isEqualTo(voucherDto.voucherId());
    }

}