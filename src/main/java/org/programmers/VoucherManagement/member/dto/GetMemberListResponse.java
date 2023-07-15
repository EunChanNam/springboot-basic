package org.programmers.VoucherManagement.member.dto;

import org.programmers.VoucherManagement.member.domain.Member;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GetMemberListResponse {
    private final List<GetMemberResponse> memberResponses;

    public GetMemberListResponse(List<Member> members) {
        this.memberResponses = members
                .stream()
                .map(GetMemberResponse::toDto).collect(Collectors.toList());
    }

    public List<GetMemberResponse> getGetMemberListRes() {
        return Collections.unmodifiableList(memberResponses);
    }
}
