package com.devcourse.springbootbasic.application.voucher.service;

import com.devcourse.springbootbasic.application.customer.model.Customer;
import com.devcourse.springbootbasic.application.customer.repository.CustomerRepository;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.voucher.model.DiscountValue;
import com.devcourse.springbootbasic.application.voucher.model.Voucher;
import com.devcourse.springbootbasic.application.voucher.model.VoucherType;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("default")
class VoucherServiceTest {

    static List<Customer> customers = List.of(
            new Customer(UUID.randomUUID(), "사과"),
            new Customer(UUID.randomUUID(), "딸기")
    );
    static List<Voucher> vouchers = List.of(
            new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "100"), customers.get(0).getCustomerId()),
            new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "0"), customers.get(0).getCustomerId()),
            new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "1240"), customers.get(1).getCustomerId()),
            new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "10"), customers.get(1).getCustomerId())
    );
    @Autowired
    VoucherService voucherService;
    @Autowired
    CustomerRepository customerRepository;
    EmbeddedMysql embeddedMysql;

    static Stream<Arguments> provideVouchers() {
        return vouchers.stream()
                .map(Arguments::of);
    }

    @BeforeAll
    void init() {
        var mysqlConfig = MysqldConfig.aMysqldConfig(Version.v8_0_17)
                .withCharset(Charset.UTF8)
                .withTimeZone("Asia/Seoul")
                .withUser("test", "test1234!")
                .withPort(8070)
                .build();
        embeddedMysql = EmbeddedMysql.anEmbeddedMysql(mysqlConfig)
                .addSchema("test-voucher_system", ScriptResolver.classPathScript("test-schema.sql"))
                .start();
        customers.forEach(customer -> customerRepository.insert(customer));
    }

    @BeforeEach
    void cleanup() {
        voucherService.deleteAllVouchers();
    }

    @AfterAll
    void destroy() {
        embeddedMysql.stop();
    }

    @ParameterizedTest
    @DisplayName("새로운 바우처가 추가되면 성공한다.")
    @MethodSource("provideVouchers")
    void createVoucher_VoucherParam_InsertAndReturnVoucher(Voucher voucher) {
        voucherService.createVoucher(voucher);
        var result = voucherService.findVoucherById(voucher.getVoucherId());
        assertThat(result, notNullValue());
        assertThat(result, instanceOf(Voucher.class));
        assertThat(result.getVoucherId(), is(voucher.getVoucherId()));
    }

    @ParameterizedTest
    @DisplayName("이미 존재하는 바우처를 또 생성하려고 하면 실패한다.")
    @MethodSource("provideVouchers")
    void createVoucher_ParamExistVoucher_Exception(Voucher voucher) {
        voucherService.createVoucher(voucher);
        Assertions.assertThrows(InvalidDataException.class, () -> voucherService.createVoucher(voucher));
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 갱신하려고 하면 성공한다.")
    @MethodSource("provideVouchers")
    void updateVoucher_ParamExistVoucher_UpdateAndReturnVoucher(Voucher voucher) {
        voucherService.createVoucher(voucher);
        var newVoucher = new Voucher(voucher.getVoucherId(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 124), voucher.getCustomerId());
        voucherService.updateVoucher(newVoucher);
        var result = voucherService.findVoucherById(newVoucher.getVoucherId());
        assertThat(result, samePropertyValuesAs(newVoucher));
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 바우처를 갱신하려고 하면 실패한다.")
    @MethodSource("provideVouchers")
    void updateVoucher_ParamNotExistVoucher_UpdateAndReturnVoucher(Voucher voucher) {
        Assertions.assertThrows(InvalidDataException.class, () -> voucherService.updateVoucher(voucher));
    }

    @Test
    @DisplayName("생성된 바우처가 리스트 형태로 반환되면 성공한다.")
    void getVouchers_VoucherMap_ReturnVouchers() {
        voucherService.createVoucher(vouchers.get(0));
        var result = voucherService.findVouchers();
        assertThat(result, notNullValue());
        assertThat(result, not(empty()));
        assertThat(result, instanceOf(List.class));
        assertThat(result.get(0), instanceOf(Voucher.class));
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 아이디로 검색하는 경우 성공한다.")
    @MethodSource("provideVouchers")
    void findVoucherById_ParamExistVoucher_ReturnVoucher(Voucher voucher) {
        voucherService.createVoucher(voucher);
        var foundVoucher = voucherService.findVoucherById(voucher.getVoucherId());
        assertThat(foundVoucher, samePropertyValuesAs(voucher));
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 바우처를 아이디로 검색하는 경우 실패한다.")
    @MethodSource("provideVouchers")
    void findVoucherById_ParamNotExistVoucher_Exception(Voucher voucher) {
        Assertions.assertThrows(InvalidDataException.class, () -> voucherService.findVoucherById(voucher.getVoucherId()));
    }

    @Test
    @DisplayName("모든 바우처를 제거한다.")
    void deleteAllVouchers_ParamVoid_DeleteAllVouchers() {
        voucherService.deleteAllVouchers();
        var vouchers = voucherService.findVouchers();
        assertThat(vouchers.isEmpty(), is(true));
    }

    @ParameterizedTest
    @DisplayName("아이디로 바우처 제거한다.")
    @MethodSource("provideVouchers")
    void deleteVoucherById_ParamVoucher_DeleteVoucher(Voucher voucher) {
        voucherService.createVoucher(voucher);
        var deletedVoucher = voucherService.deleteVoucherById(voucher.getVoucherId());
        assertThat(deletedVoucher, samePropertyValuesAs(voucher));
    }

    @ParameterizedTest
    @DisplayName("고객 아이디로 소유한 바우처를 리스트로 반환한다.")
    @MethodSource("provideVouchers")
    void findVouchersByCustomerId_ParamCustomerId_ReturnVoucher(Voucher voucher) {
        voucherService.createVoucher(voucher);
        var vouchers = voucherService.findVouchersByCustomerId(voucher.getCustomerId());
        assertThat(vouchers, instanceOf(List.class));
        assertThat(vouchers.isEmpty(), is(false));
        assertThat(vouchers.get(0), samePropertyValuesAs(voucher));
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 고객, 바우처 아이디로 삭제 시 성공한다.")
    @MethodSource("provideVouchers")
    void deleteVoucherCustomerByCustomerIdAndVoucherId_ParamIds_DeleteVoucher(Voucher voucher) {
        voucherService.createVoucher(voucher);
        var deletedVoucher = voucherService.deleteVoucherCustomerByCustomerIdAndVoucherId(voucher.getCustomerId(), voucher.getVoucherId());
        assertThat(deletedVoucher, samePropertyValuesAs(voucher));
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 바우처를 고객, 바우처 아이디로 삭제 시 실패한다.")
    @MethodSource("provideVouchers")
    void deleteVoucherCustomerByCustomerIdAndVoucherId_ParamIds_Exception(Voucher voucher) {
        Assertions.assertThrows(InvalidDataException.class, () -> voucherService.deleteVoucherCustomerByCustomerIdAndVoucherId(voucher.getCustomerId(), voucher.getVoucherId()));
    }

}
