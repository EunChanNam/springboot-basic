package org.prgrms.kdt.util;

import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.Amount;
import org.prgrms.kdt.model.FixedAmount;
import org.prgrms.kdt.model.PercentAmount;
import org.prgrms.kdt.model.dto.VoucherDTO;
import org.prgrms.kdt.model.dto.VoucherResponse;

public final class VoucherFactory {
	private static final IdGenerator idGenerator = new IdGenerator();

	private VoucherFactory() {
	}

	public static VoucherResponse getVoucherResponse(VoucherDTO voucherDTO) {
		Long voucherId = voucherDTO.getVoucherId();
		int amountValue = voucherDTO.getAmount().getAmount();
		String voucherType = voucherDTO.getVoucherType().toString();

		return new VoucherResponse(voucherId, amountValue, voucherType);
	}

	public static VoucherDTO getVoucherDTO(int amount, VoucherType voucherType) {
		Long voucherId = idGenerator.getRandomId();
		Amount voucherAmount = getVoucherAmount(amount, voucherType);
		return new VoucherDTO(voucherId, voucherAmount, voucherType);
	}

	public static Amount getVoucherAmount(int amount, VoucherType voucherType) {
		switch (voucherType) {
			case FixedAmountVoucher -> {
				return new FixedAmount(amount);
			}

			case PercentDiscountVoucher -> {
				return new PercentAmount(amount);
			}

			default -> {
				throw new IllegalArgumentException("존재 하지 않는 voucher type 입니다.");
			}
		}
	}
}
