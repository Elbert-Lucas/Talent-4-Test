package br.com.talent4.customer.mappers;

import br.com.talent4.customer.dto.AddressResponseDto;
import br.com.talent4.customer.dto.CustomerDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDtoMapper implements RowMapper<CustomerDto> {

    @Override
    public CustomerDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        CustomerDto customer = new CustomerDto();
        customer.setId(rs.getLong("id"));
        customer.setName(rs.getString("name"));
        customer.setEmail(rs.getString("email"));

        AddressResponseDto address = new AddressResponseDto();
        address.setId(rs.getLong("address_id"));
        address.setState(rs.getString("state"));
        address.setCity(rs.getString("city"));
        address.setStreet(rs.getString("street"));

        customer.setAddress(address);

        return customer;
    }
}
