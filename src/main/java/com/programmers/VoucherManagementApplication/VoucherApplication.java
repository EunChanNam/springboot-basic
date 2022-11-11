package com.programmers.VoucherManagementApplication;

import com.programmers.VoucherManagementApplication.controller.VoucherController;
import com.programmers.VoucherManagementApplication.io.Console;
import com.programmers.VoucherManagementApplication.io.Input;
import com.programmers.VoucherManagementApplication.io.Output;
import com.programmers.VoucherManagementApplication.repository.MemoryVoucherRepository;
import com.programmers.VoucherManagementApplication.service.VoucherService;

public class VoucherApplication{
    public static void main(String[] args) {
        // IoC
        new VoucherController(
                new Console(new Input(), new Output()),
                new VoucherService(new MemoryVoucherRepository())
        ).run();
        // new 연산이 너무 많은 것 같은데 괜찮은가요? 최대한 클라이언트쪽에서 할당하려 하다보니 길어집니다..
        // 스프링은 직접 주입하지 않아도 IoC
        // IoC, DI(의존성 주입)
    }
}
