package org.prgrms.VoucherManagement.voucher.voucher;

import java.util.UUID;

public interface Voucher {

  UUID getVoucherID();

  long discount(long beforeDiscount);
}
