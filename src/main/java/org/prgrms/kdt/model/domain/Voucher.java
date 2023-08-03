package org.prgrms.kdt.model.domain;

import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.Amount;
import org.prgrms.kdt.model.dto.VoucherResponse;

public final class Voucher {
	private final Long voucherId;
	private final Amount amount;
	private final VoucherType voucherType;

	public Voucher(Long voucherId, Amount amount, VoucherType voucherType) {
		this.voucherId = voucherId;
		this.amount = amount;
		this.voucherType = voucherType;
	}

	public Long getVoucherId() {
		return voucherId;
	}

	public Amount getAmount() {
		return amount;
	}

	public VoucherType getVoucherType() {
		return voucherType;
	}
}
