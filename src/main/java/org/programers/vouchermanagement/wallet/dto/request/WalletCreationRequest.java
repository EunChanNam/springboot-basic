package org.programers.vouchermanagement.wallet.dto.request;

import java.util.UUID;

public class WalletCreationRequest {

    private UUID voucherId;
    private UUID memberId;

    public WalletCreationRequest(UUID voucherId, UUID memberId) {
        this.voucherId = voucherId;
        this.memberId = memberId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public UUID getMemberId() {
        return memberId;
    }
}
