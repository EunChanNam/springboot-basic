package com.prgms.VoucherApp.controller;

import com.prgms.VoucherApp.domain.Voucher;
import com.prgms.VoucherApp.domain.VoucherPolicy;
import com.prgms.VoucherApp.model.VoucherCreator;
import com.prgms.VoucherApp.model.VoucherReader;
import com.prgms.VoucherApp.view.Command;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VoucherApp implements Runnable {

    private final VoucherCreator voucherCreator;
    private final VoucherReader voucherReader;
    private final Input input;
    private final Output output;

    public VoucherApp(VoucherCreator voucherCreator, VoucherReader voucherReader, Input input, Output output) {
        this.voucherCreator = voucherCreator;
        this.voucherReader = voucherReader;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        while (true) {
            output.printDisplayMenu();
            String inputCommand = input.inputCommand();
            Command command = Command.findByCommand(inputCommand);
            switch (command) {
                case EXIT -> {
                    return;
                }

                case CREATE -> {
                    VoucherPolicy policy = getVoucherPolicyType();
                    long amount = getDiscountAmount(policy);
                    Voucher voucher = voucherCreator.createVoucher(policy, amount);
                    output.printCreatedMsg(voucher);
                }

                case LIST -> {
                    List<Voucher> vouchers = voucherReader.readVoucherList();
                    output.printVoucherList(vouchers);
                }
            }
        }
    }

    private VoucherPolicy getVoucherPolicyType() {
        output.printDisplayVoucherPolicy();
        String inputVoucherPolicy = input.inputVoucherPolicy();
        return VoucherPolicy.findByPolicy(inputVoucherPolicy);
    }

    private long getDiscountAmount(VoucherPolicy policy) {
        output.printDisplayDiscountCondition(policy);
        return input.inputDiscountAmount(policy);
    }
}
