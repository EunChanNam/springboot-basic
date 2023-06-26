package org.programers.vouchermanagement.view;

import org.programers.vouchermanagement.member.dto.MemberResponse;
import org.programers.vouchermanagement.member.dto.MembersResponse;
import org.programers.vouchermanagement.voucher.dto.VoucherResponse;
import org.programers.vouchermanagement.voucher.dto.VouchersResponse;
import org.programers.vouchermanagement.util.Converter;

public class OutputView {

    private OutputView() {
    }

    public static void outputCommand() {
        System.out.println("명령어 리스트");
        for (Command value : Command.values()) {
            System.out.printf("%d %s%n", value.getNumber(), value);
        }
    }

    public static void outputDiscountPolicyType() {
        System.out.println("할인정책 리스트");
        for (DiscountPolicyType value : DiscountPolicyType.values()) {
            System.out.printf("%d %s%n", value.getNumber(), value);
        }
        System.out.print("번호 입력 : ");
    }

    public static void outputCommentAboutAmountOfPolicy() {
        System.out.print("고정 할인 금액을 입력 : ");
    }

    public static void outputCommentAboutPercentOfPolicy() {
        System.out.print("고정 할인 퍼센트를 입력 : ");
    }

    public static void outputVouchers(VouchersResponse response) {
        for (VoucherResponse voucher : response.getVouchers()) {
            System.out.println(Converter.toString(voucher));
        }
    }

    public static void outputMembers(MembersResponse response) {
        for (MemberResponse member : response.getMembers()) {
            System.out.println(Converter.toString(member));
        }
    }
}
