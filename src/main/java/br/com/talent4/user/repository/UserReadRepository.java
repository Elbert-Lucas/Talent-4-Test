package br.com.talent4.user.repository;

import br.com.talent4.customer.dto.CustomerDto;
import br.com.talent4.customer.mappers.CustomerDtoMapper;
import br.com.talent4.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserReadRepository {
    private final JdbcTemplate jdbcTemplate;

    private final String FIND_USER = " SELECT * FROM tb_user WHERE email = ?";

    public UserReadRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findUser(String email) {
        return jdbcTemplate.queryForObject(
                FIND_USER,
                new Object[]{email},
                new BeanPropertyRowMapper<>(User.class)
        );
    }

}
