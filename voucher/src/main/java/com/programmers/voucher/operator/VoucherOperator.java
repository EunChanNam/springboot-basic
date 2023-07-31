package com.programmers.voucher.operator;

import com.programmers.voucher.console.Console;
import com.programmers.voucher.console.Printer;
import com.programmers.voucher.domain.enums.VoucherOperation;
import com.programmers.voucher.domain.enums.VoucherType;
import com.programmers.voucher.domain.voucher.Voucher;
import com.programmers.voucher.domain.voucher.VoucherFactory;
import com.programmers.voucher.stream.voucher.VoucherStream;
import org.springframework.stereotype.Component;

@Component
public class VoucherOperator {

    private final Console console;
    private final Printer printer;
    private final VoucherFactory voucherFactory;
    private final VoucherStream voucherStream;

    public VoucherOperator(Console console, Printer printer, VoucherFactory voucherFactory, VoucherStream voucherStream) {
        this.console = console;
        this.printer = printer;
        this.voucherFactory = voucherFactory;
        this.voucherStream = voucherStream;
    }

    public void voucherOperation() {
        String operationInput = console.getVoucherOperation();
        VoucherOperation voucherType = VoucherOperation.convertStringToVoucherOperation(operationInput).orElseThrow(
                () -> new IllegalArgumentException("지원하지 않는 버전입니다. 버전을 다시 확인 해주세요.")
        );
        switch (voucherType) {
            case CREATE -> createVoucher();
            case FINDALL -> getVoucherList();
            case FINDBYID -> getVoucher();
            case UPDATE -> updateVoucher();
            case DELETEBYID -> deleteVoucher();
            case DELETEALL -> deleteAllVoucher();
        }
    }

    private void createVoucher() {
        Integer inputVersion = console.getVoucherVersion();
        VoucherType voucherType = decideVoucherType(inputVersion);
        createFixedAmountVoucher(voucherType);
        createPercentDiscountVoucher(voucherType);
    }

    private VoucherType decideVoucherType(Integer inputVersion) {
        return VoucherType.decideVoucherType(inputVersion).orElseThrow(
                () -> new IllegalArgumentException("지원하지 않는 버전입니다. 버전을 다시 확인 해주세요.")
        );
    }

    private void createFixedAmountVoucher(VoucherType voucherType) {
        if (voucherType == VoucherType.FIXED) {
            voucherFactory.createVoucher(voucherType, console.getAmount());
        }
    }

    private void createPercentDiscountVoucher(VoucherType voucherType) {
        if (voucherType == VoucherType.PERCENT) {
            voucherFactory.createVoucher(voucherType, console.getRate());
        }
    }

    private void getVoucher() {
        String voucherId = console.getVoucherId();
        printer.printVoucher(voucherStream.findById(voucherId));
    }

    private void getVoucherList() {
        printer.printListOfVoucher(voucherStream.findAll());
    }

    private void updateVoucher() {
        String voucherId = console.getVoucherId();
        Voucher voucher = voucherStream.findById(voucherId);
        voucher.update(console.readAmount());
        voucherStream.update(voucher);
    }

    private void deleteAllVoucher() {
        voucherStream.deleteAll();
    }

    private void deleteVoucher() {
        String voucherId = console.getVoucherId();
        voucherStream.deleteById(voucherId);
    }
}
