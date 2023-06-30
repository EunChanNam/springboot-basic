package com.prgrms.commandLineApplication.voucher;

import com.prgrms.commandLineApplication.voucher.discount.FixedDiscount;
import com.prgrms.commandLineApplication.voucher.discount.PercentDiscount;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherFactory {

  public static Voucher createVoucher(String voucherType, int discountAmount) {
    return switch (VoucherType.valueOfType(voucherType)) {
      case FIXED -> new Voucher(UUID.randomUUID(), FixedDiscount.of(discountAmount));
      case PERCENT -> new Voucher(UUID.randomUUID(), PercentDiscount.of(discountAmount));
    };
  }

}
