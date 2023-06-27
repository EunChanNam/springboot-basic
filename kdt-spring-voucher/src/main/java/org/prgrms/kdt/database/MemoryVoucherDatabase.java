package org.prgrms.kdt.database;

import org.prgrms.kdt.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MemoryVoucherDatabase implements VoucherDatabase {
    private final static List<Voucher> memoryVoucherDatabase = new ArrayList<>();

    @Override
    public Voucher saveVoucher(Voucher newVoucher) {
        memoryVoucherDatabase.add(newVoucher);
        return newVoucher;
    }

    @Override
    public List<Voucher> findAllVoucher() {
        return memoryVoucherDatabase;
    }
}
