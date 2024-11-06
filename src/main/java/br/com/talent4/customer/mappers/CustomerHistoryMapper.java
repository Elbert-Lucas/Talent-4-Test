package br.com.talent4.customer.mappers;

import br.com.talent4.customer.dto.AddressHistoryDto;
import br.com.talent4.customer.dto.AuthorDto;
import br.com.talent4.customer.dto.CustomerHistoryDto;
import br.com.talent4.customer.enums.CRUD;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerHistoryMapper implements RowMapper<CustomerHistoryDto> {
    @Override
    public CustomerHistoryDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        CustomerHistoryDto customerHistory = new CustomerHistoryDto();
        customerHistory.setId(rs.getLong("id"));
        customerHistory.setCustomerId(rs.getLong("customer_id"));
        customerHistory.setChangeType(CRUD.getByID(rs.getInt("change_type")));
        customerHistory.setName(rs.getString("customer.name"));
        customerHistory.setEmail(rs.getString("customer.email"));
        customerHistory.setDate(rs.getTimestamp("customer.created_at").toLocalDateTime());

        AuthorDto authorDto = new AuthorDto();
        authorDto.setName(rs.getString("user.name"));
        authorDto.setEmail(rs.getString("user.email"));

        AddressHistoryDto addressHistory = new AddressHistoryDto();
        addressHistory.setAddressId(rs.getLong("address.address_id"));
        addressHistory.setState(rs.getString("state"));
        addressHistory.setCity(rs.getString("city"));
        addressHistory.setStreet(rs.getString("street"));

        customerHistory.setAuthor(authorDto);
        customerHistory.setAddressHistory(addressHistory);

        return customerHistory;
    }
}
