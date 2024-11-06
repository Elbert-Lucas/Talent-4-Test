package br.com.talent4.customer.repository;

import br.com.talent4.customer.domain.Customer;
import br.com.talent4.customer.dto.AddressRequestDto;
import br.com.talent4.customer.dto.CustomerDto;
import br.com.talent4.customer.enums.CRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class CustomerModifyRepository{

    private final JdbcTemplate jdbcTemplate;

    private final String CREATE_COSTUMER = "INSERT INTO tb_customer(name, email, address_id, created_at) VALUES (?, ?, ?, ?)";
    private final String UPDATE_COSTUMER = "UPDATE tb_customer SET name = ?, email = ?, address_id = ? WHERE id = ?";
    private final String DELETE_COSTUMER = " DELETE FROM tb_customer WHERE id = ? ";
    private final String CREATE_ADDRESS = "INSERT INTO tb_address(state, city, street) VALUES (?, ?, ?)";
    private final String UPDATE_ADDRESS = "UPDATE tb_address SET state = ?, city = ?, street = ? WHERE id = ?";
    private final String FIND_ADDRESS_BY_USER_ID = "SELECT address_id FROM tb_customer WHERE id = ?";


    @Autowired
    public CustomerModifyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long saveCustomer(CustomerDto customer){
        KeyHolder keyHolder = new GeneratedKeyHolder();

        long addressId = saveAddress(customer.getAddress());

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(CREATE_COSTUMER, new String[] {"id"});
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setLong(3, addressId);
            ps.setTimestamp(4, Timestamp.from(Instant.now()));
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }
    private long saveAddress(AddressRequestDto address){
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(CREATE_ADDRESS, new String[] {"id"});
            ps.setString(1, address.getState());
            ps.setString(2, address.getCity());
            ps.setString(3, address.getStreet());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public int updateCustomer(long customerId, CustomerDto customerDto) {
        Long addressId = updateAddress(customerId, customerDto.getAddress());

        int rowsAffected = jdbcTemplate.update(UPDATE_COSTUMER, customerDto.getName(),
                customerDto.getEmail(), addressId, customerId);

        return rowsAffected;
    }

    private Long updateAddress(long customerId, AddressRequestDto address){
        Long id = jdbcTemplate.queryForObject(FIND_ADDRESS_BY_USER_ID, Long.class, customerId);
        jdbcTemplate.update(UPDATE_ADDRESS, address.getState(), address.getCity(), address.getStreet(), id);
        return id;
    }

    public int deleteCustomer(long customerId) {
        return jdbcTemplate.update(DELETE_COSTUMER, customerId);
    }

}
