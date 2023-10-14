package com.prgrms.springbasic.io;

import com.prgrms.springbasic.dto.VoucherResponse;

import java.util.List;

public interface Output {

    void printConsoleMessage(ConsoleMessage consoleMessage);



    void printVouchers(List<VoucherResponse> vouchers);
}
