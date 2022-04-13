package org.programmers.springbootbasic.voucher;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public enum VoucherType {

    FIXED("정량 할인 바우처", FixedDiscountVoucher.class, discountUnit.AMOUNT.getName(),
            "정해진 만큼의 금액을 깎아주는 바우처입니다."),
    RATE("비율 할인 바우처", RateDiscountVoucher.class, discountUnit.PERCENT.getName(),
            "정해진 비율만큼의 금액을 깎아주는 바우처입니다.");

    private final String name;
    private final Class<? extends Voucher> type;
    private final String discountUnitName;
    private final int ordinal;
    private final String description;

    //TODO 빌더 패턴 적용
    VoucherType(String name, Class<? extends Voucher> type, String discountUnitName, String description) {
        this.name = name;
        this.type = type;
        this.discountUnitName = discountUnitName;
        this.ordinal = this.ordinal();
        this.description = description;
    }

    public static VoucherType findTypeByOrdinal(int ordinal) {
        ordinal -= 1;     //ordinal 0부터 시작하기 때문
        for (var voucherType : VoucherType.values()) {
            if (voucherType.getOrdinal() == ordinal) {
                return voucherType;
            }
        }
        log.error("Illegal ordinal value. No corresponding voucherType found. ordinal={}", ordinal);
        throw new IllegalArgumentException(
                "Illegal ordinal value. No corresponding voucherType found. ordinal=" + ordinal);
    }

    public static String dataOfVoucher(Voucher voucher) throws IllegalStateException {
        var stringBuffer = new StringBuffer();
        var voucherTypes = VoucherType.values();
        for (VoucherType voucherType : voucherTypes) {
            log.info("vouchers={}", voucherType);
        }
        log.info("voucher's class={}", voucher.getClass());
        for (VoucherType voucherType : voucherTypes) {
            log.info("voucherType={}", voucherType.getType());
            if (voucher.getClass() == (voucherType.getType())) {
                stringBuffer.append("바우처 일련번호: ");
                stringBuffer.append(voucher.getId());
                stringBuffer.append("\n    ");
                stringBuffer.append("종류: ");
                stringBuffer.append(voucherType.getName());
                stringBuffer.append("\n    ");
                stringBuffer.append(voucherType.getDiscountUnitName());
                stringBuffer.append(": ");
                stringBuffer.append(voucher.getAmount());
                stringBuffer.append("\n    ");
                return stringBuffer.toString();
            }
        }
        stringBuffer.append("바우처 일련번호: ");
        stringBuffer.append(voucher.getId());
        stringBuffer.append("\n    ");
        stringBuffer.append("해당 종류 바우처가 정의되어 있지 않습니다: 유효하지 않은 바우처");
        return stringBuffer.toString();
    }

    public String explainThisType() {
        var stringBuffer = new StringBuffer();

        stringBuffer.append(this.getName());
        stringBuffer.append("\n    ");
        stringBuffer.append(this.getDescription());
        stringBuffer.append("\n    ");

        return stringBuffer.toString();
    }

    @RequiredArgsConstructor
    @Getter(AccessLevel.PRIVATE)
    private enum discountUnit {
        AMOUNT("할인액"), PERCENT("할인율");

        private final String name;
    }
}
