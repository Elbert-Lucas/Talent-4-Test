package br.com.talent4.user.repository;

import br.com.talent4.shared.dto.dto.RegisterUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Instant;

@Repository
public class UserModifyRepository {

    private final JdbcTemplate jdbcTemplate;

    private final String CREATE_USER = "INSERT INTO tb_user(name, email, password, created_at) VALUES (?, ?, ?, ?)";

    @Autowired
    public UserModifyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long saveUser(RegisterUserDTO registerUserDTO) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(CREATE_USER, new String[] {"id"});
            ps.setString(1, registerUserDTO.getName());
            ps.setString(2, registerUserDTO.getEmail());
            ps.setString(3, registerUserDTO.getPassword());
            ps.setTimestamp(4, Timestamp.from(Instant.now()));
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }
}
