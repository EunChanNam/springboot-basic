package org.prgrms.kdt.member.dao;

import org.prgrms.kdt.global.exception.NotUpdateException;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcMemberRepository implements MemberRepository {
    private final RowMapper<Member> memberRowMapper = (resultSet, i) -> {
        UUID memberId = UUID.fromString(resultSet.getString("id"));
        String name = resultSet.getString("name");
        MemberStatus status = MemberStatus.getStatus(resultSet.getString("status"));
        return new Member(memberId, name, status);
    };

    private final JdbcTemplate jdbcTemplate;

    public JdbcMemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Member insert(Member member) {
        String sql = "INSERT INTO member(id, name, status) VALUES (?, ?, ?)";
        int update = jdbcTemplate.update(sql, member.getMemberId().toString(),
                member.getMemberName(),
                member.getStatus().getDescripton());
        if (update != 1) {
            throw new NotUpdateException("insert가 제대로 이루어지지 않았습니다.");
        }
        return member;
    }

    @Override
    public List<Member> findAll() {
        String sql = "select id, name, status from member";
        return jdbcTemplate.query(sql, memberRowMapper);
    }

    @Override
    public Optional<Member> findById(UUID memberId) {
        String sql = "select id, name, status from member WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                    memberRowMapper,
                    memberId.toString()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Member> findByStatus(MemberStatus status) {
        String sql = "select id, name, status from member WHERE status = ?";
        return jdbcTemplate.query(sql, memberRowMapper, status.getDescripton());
    }
}
