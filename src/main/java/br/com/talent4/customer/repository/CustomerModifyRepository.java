package br.com.talent4.customer.repository;

import br.com.talent4.customer.dto.AddressRequestDto;
import br.com.talent4.customer.dto.CustomerDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;

@Repository
public class CustomerModifyRepository{

    private final JdbcTemplate jdbcTemplate;
    private final String createCustomerQuery = "INSERT INTO tb_customer(name, email, address_id, created_at) VALUES (?, ?, ?, ?)";
    private final String createAddressQuery = "INSERT INTO tb_address(state, city, street) VALUES (?, ?, ?)";

    @Autowired
    public CustomerModifyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveCustomer(CustomerDto customer){
        jdbcTemplate.update(createCustomerQuery, customer.getName(), customer.getEmail(),
                            saveAddress(customer.getAddress()), Timestamp.from(Instant.now()));

    }
    private long saveAddress(@NotNull AddressRequestDto address){
        return jdbcTemplate.update(createAddressQuery, address.getState(), address.getCity(), address.getStreet());
    }
}
