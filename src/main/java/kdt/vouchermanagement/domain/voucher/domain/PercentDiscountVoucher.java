package kdt.vouchermanagement.domain.voucher.domain;

public class PercentDiscountVoucher extends Voucher {
    private final int MIN_VALUE = 0;
    private final int MAX_VALUE = 100;

    public PercentDiscountVoucher(VoucherType voucherType, int discountValue) {
        super(voucherType, discountValue);
    }

    public PercentDiscountVoucher(Long voucherId, VoucherType voucherType, int discountValue) {
        super(voucherId, voucherType, discountValue);
    }

    @Override
    public void validateValueRange() {
        if (this.getDiscountValue() <= MIN_VALUE || this.getDiscountValue() > MAX_VALUE) {
            throw new IllegalArgumentException("입력한 DiscountValue 값이 유효하지 않습니다.");
        }
    }
}
