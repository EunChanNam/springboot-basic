package com.prgms.VoucherApp.domain.voucher.controller;


import com.prgms.VoucherApp.domain.voucher.dto.VoucherCreateRequest;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherUpdateRequest;
import com.prgms.VoucherApp.domain.voucher.dto.VouchersResponse;
import com.prgms.VoucherApp.domain.voucher.model.VoucherDaoHandler;
import com.prgms.VoucherApp.domain.voucher.model.VoucherType;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;
import com.prgms.VoucherApp.view.VoucherCommand;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.UUID;

@Controller
public class VoucherManagementController implements Runnable {

    private final VoucherDaoHandler voucherDaoHandler;
    private final Input input;
    private final Output output;

    public VoucherManagementController(VoucherDaoHandler voucherDaoHandler, Input input, Output output) {
        this.voucherDaoHandler = voucherDaoHandler;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        boolean isRunning = true;
        while (isRunning) {
            try {
                output.printVoucherCommand();
                Integer inputCommandNumber = input.inputVoucherCommand();
                VoucherCommand command = VoucherCommand.findByVoucherCommandNumber(inputCommandNumber);

                switch (command) {
                    case CREATE -> {
                        output.printDisplayVoucherPolicy();
                        String inputVoucherType = input.inputVoucherType();

                        VoucherType voucherType = VoucherType.findByVoucherTypeName(inputVoucherType);
                        output.printDisplayDiscountCondition(voucherType);
                        Long amount = input.inputDiscountAmount(voucherType);

                        VoucherCreateRequest voucherCreateRequest = new VoucherCreateRequest(voucherType, BigDecimal.valueOf(amount));

                        voucherDaoHandler.save(voucherCreateRequest);
                    }

                    case FIND_ALL -> {
                        VouchersResponse findVouchers = voucherDaoHandler.findAll();
                        output.printVoucherList(findVouchers.getVouchers());
                    }

                    case FIND_ONE -> {
                        String inputUUID = input.inputUUID();
                        UUID voucherId = UUID.fromString(inputUUID);

                        voucherDaoHandler.findOne(voucherId)
                            .ifPresentOrElse(output::printVoucher, output::printFindEmpty);
                    }

                    case FIND_BY_VOUCHER_TYPE -> {
                        String inputVoucherType = input.inputVoucherType();
                        VoucherType voucherType = VoucherType.findByVoucherTypeName(inputVoucherType);

                        VouchersResponse findVouchers = voucherDaoHandler.findByVoucherType(voucherType);

                        output.printVoucherList(findVouchers.getVouchers());
                    }

                    case UPDATE -> {
                        String inputUUID = input.inputUUID();
                        UUID voucherId = UUID.fromString(inputUUID);

                        String inputVoucherType = input.inputVoucherType();
                        VoucherType voucherType = VoucherType.findByVoucherTypeName(inputVoucherType);

                        Long inputAmount = input.inputDiscountAmount(voucherType);
                        BigDecimal amount = BigDecimal.valueOf(inputAmount);

                        VoucherUpdateRequest voucherUpdateRequest = new VoucherUpdateRequest(voucherId, amount, voucherType);
                        voucherDaoHandler.update(voucherUpdateRequest);
                    }

                    case DELETE -> {
                        String inputUUID = input.inputUUID();
                        UUID voucherId = UUID.fromString(inputUUID);

                        voucherDaoHandler.deleteById(voucherId);
                    }

                    case EXIT -> {
                        isRunning = false;
                    }

                    default -> {
                        output.printNotImplementMsg();
                    }
                }
            } catch (RuntimeException exception) {
                output.printErrorMsg(exception.getMessage());
            }
        }
    }
}
