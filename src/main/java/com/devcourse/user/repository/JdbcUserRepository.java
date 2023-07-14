package com.devcourse.user.repository;

import com.devcourse.user.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
class JdbcUserRepository implements UserRepository {
    private static final RowMapper<User> userMapper = (resultSet, resultNumber) -> {
        UUID id = UUID.fromString(resultSet.getString("id"));
        String name = resultSet.getString("name");
        return new User(id, name);
    };

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UUID save(String name) {
        UUID id = UUID.randomUUID();
        jdbcTemplate.update("INSERT INTO users(id, name) VALUES (?, ?)",
                id.toString(),
                name);

        return id;
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users", userMapper);
    }

    @Override
    public Optional<User> findById(UUID id) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?",
                    userMapper,
                    id.toString()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(UUID id) {
        jdbcTemplate.update("DELETE FROM users WHERE id = ?",
                id.toString());
    }

    @Override
    public void update(UUID id, String name) {
        jdbcTemplate.update("UPDATE users SET name = ? WHERE id = ?",
                name,
                id.toString());
    }
}
