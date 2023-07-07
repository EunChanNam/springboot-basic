package kr.co.springbootweeklymission;

import kr.co.springbootweeklymission.member.presentation.MemberController;
import kr.co.springbootweeklymission.member.presentation.dto.request.MemberReqDTO;
import kr.co.springbootweeklymission.view.Command;
import kr.co.springbootweeklymission.view.InputView;
import kr.co.springbootweeklymission.view.OutputView;
import kr.co.springbootweeklymission.voucher.domain.model.VoucherPolicy;
import kr.co.springbootweeklymission.voucher.presentation.VoucherController;
import kr.co.springbootweeklymission.voucher.presentation.dto.request.VoucherReqDTO;
import kr.co.springbootweeklymission.wallet.presentation.WalletController;
import kr.co.springbootweeklymission.wallet.presentation.dto.request.WalletReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

import java.util.UUID;

@RequiredArgsConstructor
public class VoucherProgramController implements CommandLineRunner {
    private final MemberController memberController;
    private final VoucherController voucherController;
    private final WalletController walletController;

    private static boolean IS_RUNNING = true;

    @Override
    public void run(String... args) {
        do {
            OutputView.outputCommand();
            Command command = InputView.inputCommand();

            if (command.isCreateMember()) {
                OutputView.outputCreateMember();
                OutputView.outputMemberStatus();
                final MemberReqDTO.CREATE create = MemberReqDTO.CREATE.builder()
                        .memberStatus(InputView.inputMemberStatus())
                        .build();
                memberController.createMember(create);
                continue;
            }

            if (command.isUpdateMember()) {
                OutputView.outputUpdateMember();
                OutputView.outputMemberStatus();
                UUID memberId = UUID.fromString(InputView.inputMemberId());
                final MemberReqDTO.UPDATE update = MemberReqDTO.UPDATE.builder()
                        .memberStatus(InputView.inputMemberStatus())
                        .build();
                memberController.updateMemberById(memberId, update);
                continue;
            }

            if (command.isDeleteMember()) {
                OutputView.outputDeleteMember();
                memberController.deleteMemberById(UUID.fromString(InputView.inputMemberId()));
                continue;
            }

            if (command.isReadMember()) {
                OutputView.outputMember(memberController.getMemberById(UUID.fromString(InputView.inputMemberId())));
                continue;
            }

            if (command.isReadAllBlackMember()) {
                OutputView.outputBlackMembers(memberController.getMembersByBlack());
                continue;
            }

            if (command.isCreateVoucher()) {
                OutputView.outputCreateVoucher();
                OutputView.outputVoucherPolicy();
                final VoucherReqDTO.CREATE create = VoucherReqDTO.CREATE.builder()
                        .voucherPolicy(InputView.inputVoucherPolicy())
                        .amount(InputView.inputAmount())
                        .build();
                voucherController.createVoucher(create);
                continue;
            }

            if (command.isUpdateVoucher()) {
                OutputView.outputUpdateVoucher();
                OutputView.outputVoucherPolicy();
                UUID voucherId = UUID.fromString(InputView.inputVoucherId());
                final VoucherReqDTO.UPDATE update = VoucherReqDTO.UPDATE.builder()
                        .voucherPolicy(VoucherPolicy.valueOf(InputView.inputVoucherPolicy()))
                        .amount(InputView.inputAmount())
                        .build();
                voucherController.updateVoucherById(voucherId, update);
                continue;
            }

            if (command.isDeleteVoucher()) {
                OutputView.outputDeleteVoucher();
                voucherController.deleteVoucherById(UUID.fromString(InputView.inputVoucherId()));
                continue;
            }

            if (command.isReadVoucher()) {
                OutputView.outputVoucher(voucherController.getVoucherById(UUID.fromString(InputView.inputVoucherId())));
                continue;
            }

            if (command.isReadAllVouchers()) {
                OutputView.outputVouchers(voucherController.getVouchersAll());
                continue;
            }

            if (command.isCreateVoucherMember()) {
                OutputView.outputCreateVoucherMember();
                final WalletReqDTO.CREATE create = WalletReqDTO.CREATE.builder()
                        .voucherId(UUID.fromString(InputView.inputVoucherId()))
                        .memberId(UUID.fromString(InputView.inputMemberId()))
                        .build();
                walletController.createVoucherMember(create);
                continue;
            }

            if (command.isReadVouchersByMember()) {
                OutputView.outputVouchers(walletController.getVouchersByMemberId(UUID.fromString(InputView.inputMemberId())));
                continue;
            }

            if (command.isReadMemberByVoucher()) {
                OutputView.outputMember(walletController.getMemberByVoucherId(UUID.fromString(InputView.inputVoucherId())));
                continue;
            }

            if (command.isDeleteVoucherMember()) {
                OutputView.outputDeleteVoucherMember();
                final WalletReqDTO.DELETE delete = WalletReqDTO.DELETE.builder()
                        .voucherId(UUID.fromString(InputView.inputVoucherId()))
                        .memberId(UUID.fromString(InputView.inputMemberId()))
                        .build();
                walletController.deleteVoucherMemberByVoucherIdAndMemberId(delete);
                continue;
            }

            IS_RUNNING = false;

        } while (IS_RUNNING);
    }
}
