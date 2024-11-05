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
    private final String CREATE_COSTUMER_AUD = "INSERT INTO tb_customer_aud(customer_id, change_type, name, email, address_id, created_at) VALUES (?, ?, ?, ?, ?, ?)";
    private final String UPDATE_COSTUMER = "UPDATE tb_customer SET name = ?, email = ?, address_id = ? WHERE id = ?";
    private final String FIND_COSTUMER_BY_ID = "SELECT * FROM tb_customer customer LEFT JOIN tb_address address ON customer.address_id = address.id WHERE customer.id = ?";
    private final String CREATE_ADDRESS = "INSERT INTO tb_address(state, city, street) VALUES (?, ?, ?)";
    private final String CREATE_ADDRESS_AUD = "INSERT INTO tb_address_aud(address_id, change_type, state, city, street, created_at) VALUES (?, ?, ?, ?, ?, ?)";
    private final String UPDATE_ADDRESS = "UPDATE tb_address SET state = ?, city = ?, street = ? WHERE id = ?";
    private final String FIND_ADDRESS_BY_USER_ID = "SELECT address_id FROM tb_customer WHERE id = ?";


    @Autowired
    public CustomerModifyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long saveCustomer(CustomerDto customer){
        KeyHolder keyHolder = new GeneratedKeyHolder();

        Timestamp createdTimestamp = Timestamp.from(Instant.now());
        long addressId = saveAddress(customer.getAddress());

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(CREATE_COSTUMER, new String[] {"id"});
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setLong(3, addressId);
            ps.setTimestamp(4, createdTimestamp);
            return ps;
        }, keyHolder);

        saveCustomerAud(customer, keyHolder.getKey().longValue(), addressId, createdTimestamp, CRUD.CREATE);

        return keyHolder.getKey().longValue();
    }
    private long saveAddress(AddressRequestDto address){
        KeyHolder keyHolder = new GeneratedKeyHolder();

        Timestamp createdTimestamp = Timestamp.from(Instant.now());

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(CREATE_ADDRESS, new String[] {"id"});
            ps.setString(1, address.getState());
            ps.setString(2, address.getCity());
            ps.setString(3, address.getStreet());
            return ps;
        }, keyHolder);

        saveAddressAud(address, keyHolder.getKey().longValue(), createdTimestamp, CRUD.CREATE);
        return keyHolder.getKey().longValue();
    }


    public int updateCustomer(long customerId, CustomerDto customerDto) {
        Timestamp timestamp = Timestamp.from(Instant.now());

        Map<String, Long> addressId = updateAddress(customerId, customerDto.getAddress());

        int rowsAffected = jdbcTemplate.update(UPDATE_COSTUMER, customerDto.getName(),
                customerDto.getEmail(), addressId.get("id"), customerId);

        saveCustomerAud(customerDto, customerId, addressId.get("aud_id"), timestamp, CRUD.UPDATE);

        return rowsAffected;
    }

    private Map updateAddress(long customerId, AddressRequestDto address){
        Map<String, Long> ids = new HashMap<>();
        Timestamp timestamp = Timestamp.from(Instant.now());

        Long id = jdbcTemplate.queryForObject(FIND_ADDRESS_BY_USER_ID, Long.class, customerId);
        jdbcTemplate.update(UPDATE_ADDRESS, address.getState(), address.getCity(), address.getStreet(), id);

        ids.put("id", id);
        ids.put("aud_id", saveAddressAud(address, id, timestamp, CRUD.UPDATE));

        return ids;
    }

    private void saveCustomerAud(CustomerDto customer, Long customerId, Long addressAudId, Timestamp timestamp, CRUD changeType){
        jdbcTemplate.update(CREATE_COSTUMER_AUD, customerId, changeType.getValue(), customer.getName(),
                customer.getEmail(), addressAudId, timestamp);
    }
    private long saveAddressAud(AddressRequestDto address, Long addressId, Timestamp timestamp, CRUD changeType){
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(CREATE_ADDRESS_AUD, new String[] {"id"});
            ps.setLong(1, addressId);
            ps.setInt(2, changeType.getValue());
            ps.setString(3, address.getState());
            ps.setString(4, address.getCity());
            ps.setString(5, address.getStreet());
            ps.setTimestamp(6, timestamp);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }
}
