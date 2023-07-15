package com.prgrms.commandLineApplication.voucher;

import com.prgrms.commandLineApplication.voucher.discount.Discount;
import com.prgrms.commandLineApplication.voucher.discount.DiscountFactory;

import java.util.UUID;

public class VoucherFactory {

  public static Voucher of(UUID voucherId, String discountType, int discountAmount) {
    Discount discount = DiscountFactory.of(discountType, discountAmount);
    return new Voucher(voucherId, discount);
  }

}
