package com.pppp0722.vouchermanagement.service.member;

import com.pppp0722.vouchermanagement.entity.member.Member;
import com.pppp0722.vouchermanagement.repository.member.MemberRepository;
import com.pppp0722.vouchermanagement.service.member.MemberService;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member createMember(UUID memberId, String name) {
        Member member = new Member(memberId, name);
        memberRepository.createMember(member);

        return member;
    }

    @Override
    public void updateWallet(UUID memberId, UUID walletId) {
        memberRepository.updateWallet(memberId, walletId);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.readMembers();
    }
}