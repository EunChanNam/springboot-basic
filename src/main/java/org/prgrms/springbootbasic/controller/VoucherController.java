package org.prgrms.springbootbasic.controller;

import org.prgrms.springbootbasic.NotificationProperties;
import org.prgrms.springbootbasic.entity.voucher.Voucher;
import org.prgrms.springbootbasic.message.Response;
import org.prgrms.springbootbasic.service.VoucherService;
import org.prgrms.springbootbasic.type.MethodType;
import org.prgrms.springbootbasic.type.VoucherType;
import org.prgrms.springbootbasic.util.CommandLineInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static org.prgrms.springbootbasic.type.MethodType.isCreateVoucher;
import static org.prgrms.springbootbasic.type.MethodType.isLookupList;


@Controller
public class VoucherController {
    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);
    private final NotificationProperties notificationProperties;
    private final VoucherService voucherService;

    @Autowired
    VoucherController(VoucherService voucherService, NotificationProperties notificationProperties) {
        this.voucherService = voucherService;
        this.notificationProperties = notificationProperties;
    }

    public Response process() {
        String method = CommandLineInput.getInput(notificationProperties.getVoucherPrompt());
        while (!MethodType.validate(method)) {
            System.out.println(notificationProperties.getWrongInput());
            method = CommandLineInput.getInput(notificationProperties.getVoucherPrompt());
        }
        List<Voucher> voucherList = new ArrayList<>();
        if (isCreateVoucher(method)) {
            voucherList.add(createVoucherProcess());
        } else if (isLookupList(method)) {
            voucherList.addAll(voucherService.lookupVoucherList());
        }
        return new Response(MethodType.valueOf(method.toUpperCase()), voucherList);
    }

    private Voucher createVoucherProcess() {
        VoucherType voucherType = getVoucherType();
        String quantity = getQuantity(voucherType);
        try {
            return voucherService.createVoucher(voucherType, Long.parseLong(quantity));
        } catch (IllegalArgumentException e) {
            logger.error("Got error in voucher creation: {}", e.getMessage());
            return createVoucherProcess();
        }
    }

    private String getQuantity(VoucherType voucherType) {
        return CommandLineInput.getInput(MessageFormat.format("Type {0} :", voucherType.getArgument()));
    }

    private VoucherType getVoucherType() {
        String voucherTypeNumber;
        do {
            voucherTypeNumber = CommandLineInput.getInput(notificationProperties.getVoucherTypeChoice());
        } while (VoucherType.isFixed(voucherTypeNumber) && VoucherType.isPercent(voucherTypeNumber));
        return VoucherType.NumberToVoucherType(voucherTypeNumber);
    }
}
