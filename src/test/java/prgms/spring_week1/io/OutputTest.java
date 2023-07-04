package prgms.spring_week1.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import prgms.spring_week1.domain.customer.model.BlackConsumer;
import prgms.spring_week1.domain.voucher.model.impl.FixedAmountVoucher;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.domain.voucher.repository.VoucherRepository;
import prgms.spring_week1.domain.voucher.repository.impl.MemoryVoucherRepository;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OutputTest {
    private static ByteArrayOutputStream outputMessage;
    private static VoucherRepository voucherRepository;
    private static Output output;

    @BeforeEach
    void setUp() {
        outputMessage = new ByteArrayOutputStream();
        voucherRepository = new MemoryVoucherRepository();
        output = new Output();
        System.setOut(new PrintStream(outputMessage));
    }

    @Test
    @DisplayName("바우처를 조회 했을 때 제대로 출력되는 지 확인")
    void printVoucherInfo() {
        //given
        voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), VoucherType.FIXED, 10000));
        voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), VoucherType.PERCENT, 10));

        //when
        output.printDiscountFixedVoucherInfo(10000);
        output.printDiscountAmountVoucherInfo(10);

        //then
        assertEquals("상품권 종류 : 고정 가격 할인 상품권 할인 가격 :10000원\n" +
                "상품권 종류 : 고정 가격 할인 상품권 할인률 :10 퍼센트", outputMessage.toString().strip());
    }

    @Test
    @DisplayName("블랙리스트를 조회 했을 때 제대로 출력되는 지 확인")
    void printBlackConsumerList() {
        //given
        List<BlackConsumer> blackConsumerList = new ArrayList<>();
        blackConsumerList.add(new BlackConsumer("오세한", "20"));
        blackConsumerList.add(new BlackConsumer("세오한", "21"));
        blackConsumerList.add(new BlackConsumer("세한오", "22"));

        //when
        output.printBlackConsumerList(blackConsumerList);

        //then
        assertEquals("오세한 20\n" +
                "세오한 21\n" +
                "세한오 22", outputMessage.toString().strip());
    }
}
