package com.prgrms.voucher.model;

import com.prgrms.order.model.OrderItem;
import com.prgrms.order.model.Price;
import com.prgrms.voucher.model.discount.Discount;
import java.time.LocalDateTime;

public abstract class Voucher {

    private final int voucherId;
    private final Discount discount;
    private final VoucherType voucherType;
    private final LocalDateTime createdAt;
    private boolean deleted = false;

    public Voucher(int voucherId, Discount discount, VoucherType voucherType,
            LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.discount = discount;
        this.voucherType = voucherType;
        this.createdAt = createdAt;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public Discount getVoucherDiscount() {
        return discount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Price discountPrice(OrderItem orderItem) {
        Price originalPrice = orderItem.productPrice();
        return getVoucherDiscount().sale(originalPrice);
    }

}
