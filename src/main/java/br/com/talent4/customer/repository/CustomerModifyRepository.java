package br.com.talent4.customer.repository;

import br.com.talent4.customer.dto.AddressRequestDto;
import br.com.talent4.customer.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Instant;

@Repository
public class CustomerModifyRepository{

    private final JdbcTemplate jdbcTemplate;

    private final String CREATE_CUSTOMER = " INSERT INTO tb_customer(name, email, address_id, last_author, created_at) VALUES (?, ?, ?, ?, ?) ";
    private final String UPDATE_CUSTOMER = " UPDATE tb_customer SET name = ?, email = ?, address_id = ?, last_author = ? WHERE id = ? ";
    private final String SET_AUTHOR_DEL = " SET @author_of_del = ?  ";
    private final String DELETE_CUSTOMER = " DELETE FROM tb_customer WHERE id = ? ";
    private final String CREATE_ADDRESS = " INSERT INTO tb_address(state, city, street) VALUES (?, ?, ?) ";
    private final String UPDATE_ADDRESS = " UPDATE tb_address SET state = ?, city = ?, street = ? WHERE id = ? ";
    private final String FIND_ADDRESS_BY_USER_ID = " SELECT address_id FROM tb_customer WHERE id = ? ";


    @Autowired
    public CustomerModifyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long saveCustomer(CustomerDto customer){
        KeyHolder keyHolder = new GeneratedKeyHolder();

        long addressId = saveAddress(customer.getAddress());

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(CREATE_CUSTOMER, new String[] {"id"});
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setLong(3, addressId);
            ps.setLong(4, getAuthor());
            ps.setTimestamp(5, Timestamp.from(Instant.now()));
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

        int rowsAffected = jdbcTemplate.update(UPDATE_CUSTOMER, customerDto.getName(),
                customerDto.getEmail(), addressId,getAuthor(), customerId);

        return rowsAffected;
    }

    private Long updateAddress(long customerId, AddressRequestDto address){
        Long id = jdbcTemplate.queryForObject(FIND_ADDRESS_BY_USER_ID, Long.class, customerId);
        jdbcTemplate.update(UPDATE_ADDRESS, address.getState(), address.getCity(), address.getStreet(), id);
        return id;
    }

    public int deleteCustomer(long customerId) {
        jdbcTemplate.update(SET_AUTHOR_DEL, getAuthor());
        return jdbcTemplate.update(DELETE_CUSTOMER, customerId);
    }
    private Long getAuthor(){
        return (Long) ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest()
                .getAttribute("user");
    }

}
