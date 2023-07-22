package org.prgrms.kdt.wallet.controller;

import org.prgrms.kdt.wallet.controller.dto.CreateWalletControllerRequest;
import org.prgrms.kdt.wallet.controller.mapper.ControllerWalletMapper;
import org.prgrms.kdt.wallet.service.dto.JoinedWalletResponses;
import org.prgrms.kdt.wallet.service.WalletService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class WalletController {
    private final WalletService walletService;
    private final ControllerWalletMapper mapper;

    public WalletController(WalletService walletService, ControllerWalletMapper mapper) {
        this.walletService = walletService;
        this.mapper = mapper;
    }

    public void createWallet(CreateWalletControllerRequest request) {
        walletService.assignVoucherToCustomer(mapper.controllerRequestToServiceRequest(request));
    }

    public JoinedWalletResponses findVouchersByMemberId(UUID memberId) {
        return walletService.findVouchersByMemberId(memberId);
    }

    public JoinedWalletResponses findMembersByVoucherId(UUID voucherId) {
        return walletService.findMembersByVoucherId(voucherId);
    }

    public void deleteWalletById(UUID walletId) {
        walletService.deleteWalletById(walletId);
    }

    public JoinedWalletResponses findAllWallet() {
        return walletService.findAllWallet();
    }
}
