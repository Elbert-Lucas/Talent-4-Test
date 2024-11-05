package br.com.talent4.customer.repository;

import br.com.talent4.customer.domain.Customer;
import br.com.talent4.customer.dto.AddressRequestDto;
import br.com.talent4.customer.dto.CustomerDto;
import jakarta.validation.constraints.NotNull;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Repository
public class CustomerModifyRepository{

    private final JdbcTemplate jdbcTemplate;
    private final String createCustomerQuery = "INSERT INTO tb_customer(name, email, address_id, created_at) VALUES (?, ?, ?, ?)";
    private final String updateCustomerQuery = "UPDATE tb_customer SET name = ?, email = ?, address_id = ? WHERE id = ?";
    private final String findCustomerByIdQuery = "SELECT * FROM tb_customer customer LEFT JOIN tb_address address ON customer.address_id = address.id WHERE customer.id = ?";
    private final String createAddressQuery = "INSERT INTO tb_address(state, city, street) VALUES (?, ?, ?)";
    private final String updateAddressQuery = "UPDATE tb_address SET state = ?, city = ?, street = ? WHERE id = ?";
    private final String findAddressByUserIdQuery = "SELECT * FROM tb_address WHERE customer_id = ?";
    private final String findAddressIdByUserIdQuery = "SELECT address_id FROM tb_customer WHERE id = ?";


    @Autowired
    public CustomerModifyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long saveCustomer(CustomerDto customer){
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(createCustomerQuery, new String[] {"id"});
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setLong(3, saveAddress(customer.getAddress()));
            ps.setTimestamp(4, Timestamp.from(Instant.now()));
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }
    private long saveAddress(AddressRequestDto address){
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(createAddressQuery, new String[] {"id"});
            ps.setString(1, address.getState());
            ps.setString(2, address.getCity());
            ps.setString(3, address.getStreet());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public Optional<Customer> findCustomerById(long customerId) {
        return null;
    }

    public int updateCustomer(long customerId, CustomerDto customerDto) {
        long addressId = updateAddress(customerId, customerDto.getAddress());
        return jdbcTemplate.update(updateCustomerQuery, customerDto.getName(),
                customerDto.getEmail(), addressId, customerId);
    }

    private long updateAddress(long customerId, AddressRequestDto address){
        Long id = jdbcTemplate.queryForObject(findAddressIdByUserIdQuery, Long.class, customerId);
        jdbcTemplate.update(updateAddressQuery, address.getState(), address.getCity(), address.getStreet(), id);
        return id;
    }

}

