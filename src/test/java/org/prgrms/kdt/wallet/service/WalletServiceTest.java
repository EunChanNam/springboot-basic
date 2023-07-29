package org.prgrms.kdt.wallet.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.global.exception.EntityNotFoundException;
import org.prgrms.kdt.member.dao.MemberRepository;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.prgrms.kdt.voucher.dao.VoucherRepository;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.wallet.dao.WalletRepository;
import org.prgrms.kdt.wallet.domain.JoinedWallet;
import org.prgrms.kdt.wallet.domain.Wallet;
import org.prgrms.kdt.wallet.service.dto.CreateWalletRequest;
import org.prgrms.kdt.wallet.service.dto.JoinedWalletResponses;
import org.prgrms.kdt.wallet.service.dto.WalletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class WalletServiceTest {

    @Autowired
    WalletService walletService;
    @Autowired
    WalletRepository walletRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    VoucherRepository voucherRepository;

    @BeforeEach
    void setup() {
        /**
         *  테스트용 db의 멤버와 바우처 테이블에 각각 초기 레코드 2개씩 셋팅해 놓은 상태,
         *  이 중 멤버1에게 바우처 두개를 할당한 상태로 초기화 되었다.
         */
        setupInsertWallets();
    }

    @Test
    @DisplayName("올바른 멤버와 바우처가 담긴 request객체를 통해 월렛 할당 후 반환된 월렛안의 멤버Id 비교")
    void assignVoucherToCustomer_correctRequest_correctWalletResponse() {
        //given
        UUID expectMemberId = UUID.fromString("9a3d5b3e-2d12-4958-9ef3-52d424485895");
        Member member = memberRepository.insert(new Member(expectMemberId, "giho", MemberStatus.COMMON));
        Voucher voucher = voucherRepository.insert(new Voucher(UUID.randomUUID(), VoucherType.FIXED, VoucherType.FIXED.createPolicy(35.0), LocalDateTime.now()));
        CreateWalletRequest request = new CreateWalletRequest(UUID.randomUUID(), member.getMemberId(), voucher.getVoucherId());

        //when
        WalletResponse resultWallet = walletService.assignVoucherToCustomer(request);

        //then
        UUID resultMemberId = resultWallet.memberId();
        assertThat(resultMemberId).isEqualTo(expectMemberId);
    }

    @Test
    @DisplayName("존재하지 않는 바우처가 담긴 request객체를 통해 월렛 할당 시 EntityNotFoundException 확인")
    void assignVoucherToCustomer_incorrectRequest_EntityNotFoundException() {
        //given
        Member member = memberRepository.insert(new Member(UUID.randomUUID(), "giho", MemberStatus.COMMON));
        Voucher voucher = new Voucher(UUID.randomUUID(), VoucherType.FIXED, VoucherType.FIXED.createPolicy(30.0), LocalDateTime.now());
        CreateWalletRequest request = new CreateWalletRequest(UUID.randomUUID(), member.getMemberId(), voucher.getVoucherId());

        //when
        Exception exception = catchException(() -> walletService.assignVoucherToCustomer(request));

        //then
        assertThat(exception).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("setup을 통해 바우처 2개를 할당받은 멤버Id를 조회하여 Response의 size가 2인지 확인")
    void findVouchersByMemberId_correctMemberId_correctResponseSize() {
        //when
        JoinedWalletResponses responseList = walletService.findVouchersByMemberId(UUID.fromString("1a3d5b3e-2d12-4958-9ef3-52d424485895"));

        //then
        int responseSize = responseList.wallets().size();
        assertThat(responseSize).isEqualTo(2);
    }

    @Test
    @DisplayName("setup을 통해 바우처 2개를 가진 멤버1의 바우처 하나 삭제 후 멤버1의 바우처 개수 조회")
    void deleteWalletById_correctId_correctResponseSize() {
        //when
        walletService.deleteWalletById(UUID.fromString("f7c23946-7174-4f56-b464-3ed1fa5224d7"));

        //then
        List<JoinedWallet> findJoinedWalletList = walletRepository.findWithMemeberAndVoucherByMemberId(UUID.fromString("1a3d5b3e-2d12-4958-9ef3-52d424485895"));
        assertThat(findJoinedWalletList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("setup을 통해 해당 바우처를 할당받은 james를 voucherId를 통해 찾아서 확인")
    void findMembersByVoucherId_correctVoucherId_correctMemberName() {
        //when
        JoinedWalletResponses response = walletService.findMembersByVoucherId(UUID.fromString("3c3dda5e-eb09-4b21-b57f-d9ef54bacd29"));

        //then
        String findMemberName = response.wallets().get(0).memberName();
        assertThat(findMemberName).isEqualTo("james");
    }

    @Test
    @DisplayName("setup을 통해 저장된 월렛2개 전체 조회를 통해 사이즈 확인")
    void findAllWallet_collectWalletSize() {
        //when
        List<JoinedWallet> joinedWallets = walletRepository.findWithMemeberAndVoucherAll();

        //then
        assertThat(joinedWallets.size()).isEqualTo(2);
    }

    void setupInsertWallets() {
        UUID memberId1 = UUID.fromString("1a3d5b3e-2d12-4958-9ef3-52d424485895");
        UUID memberId2 = UUID.fromString("3a3d3a3e-2d12-4958-9ef3-52d424485895");
        Member member1 = new Member(memberId1, "james", MemberStatus.COMMON);
        Member member2 = new Member(memberId2, "lala", MemberStatus.COMMON);

        UUID voucherId1 = UUID.fromString("3c3dda5e-eb09-4b21-b57f-d9ef54bacd29");
        UUID voucherId2 = UUID.fromString("5c3aba5e-eb09-4b21-b57f-d9ef54bacd29");
        Voucher voucher1 = new Voucher(voucherId1, VoucherType.FIXED, VoucherType.FIXED.createPolicy(30.0), LocalDateTime.now());
        Voucher voucher2 = new Voucher(voucherId2, VoucherType.PERCENT, VoucherType.PERCENT.createPolicy(70.0), LocalDateTime.now());

        UUID walletId1 = UUID.fromString("f7c23946-7174-4f56-b464-3ed1fa5224d7");
        UUID walletId2 = UUID.fromString("c9c23946-7174-4f56-b464-3ed1fa5224d7");
        Wallet wallet1 = new Wallet(walletId1, memberId1, voucherId1);
        Wallet wallet2 = new Wallet(walletId2, memberId1, voucherId2);

        memberRepository.insert(member1);
        memberRepository.insert(member2);
        voucherRepository.insert(voucher1);
        voucherRepository.insert(voucher2);
        walletRepository.insert(wallet1);
        walletRepository.insert(wallet2);
    }
}