package br.com.talent4.customer.mappers;

import br.com.talent4.customer.dto.AddressHistoryDto;
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
        customerHistory.setName(rs.getString("name"));
        customerHistory.setEmail(rs.getString("email"));
        customerHistory.setDate(rs.getTimestamp("customer.created_at"));

        AddressHistoryDto addressHistory = new AddressHistoryDto();
        addressHistory.setAddressId(rs.getLong("address.address_id"));
        addressHistory.setState(rs.getString("state"));
        addressHistory.setCity(rs.getString("city"));
        addressHistory.setStreet(rs.getString("street"));

        customerHistory.setAddressHistory(addressHistory);

        return customerHistory;
    }
}
