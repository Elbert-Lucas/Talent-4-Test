package br.com.talent4.user.repository;

import br.com.talent4.user.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class UserReadRepository {
    private final JdbcTemplate jdbcTemplate;

    private final String FIND_USER = " SELECT * FROM tb_user WHERE email = ? OR id = ?";

    public UserReadRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findUserByEmailOrId(String value) {
        return jdbcTemplate.queryForObject(
                FIND_USER,
                new Object[]{value, value},
                new BeanPropertyRowMapper<>(User.class)
        );
    }
}

