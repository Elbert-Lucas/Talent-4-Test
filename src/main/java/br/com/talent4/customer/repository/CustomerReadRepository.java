package br.com.talent4.customer.repository;

import br.com.talent4.customer.dto.CustomerDto;
import br.com.talent4.customer.dto.CustomerHistoryDto;
import br.com.talent4.customer.mappers.CustomerDtoMapper;
import br.com.talent4.customer.mappers.CustomerHistoryMapper;
import br.com.talent4.customer.util.AddressUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLSyntaxErrorException;
import java.util.Arrays;
import java.util.List;

@Repository
public class CustomerReadRepository {

    private final JdbcTemplate jdbcTemplate;

    private final String FIND_CUSTOMERS = " SELECT * FROM tb_customer customer LEFT JOIN tb_address address ON address.id = customer.address_id ";
    private final String WHERE_STATE = " WHERE address.state = '?' " ;
    private final String ORDER_BY = " ORDER BY ? ";
    private final String PAGEABLE = " LIMIT ? OFFSET ? ";
    private final String COUNT_TOTAL = " SELECT COUNT(*) FROM tb_customer ";

    private final String FIND_CUSTOMERS_HISTORY = " SELECT * FROM tb_customer_aud customer LEFT JOIN tb_address_aud address ON address.id = customer.address_id  WHERE customer_id = ? ORDER BY customer.created_at DESC ";

    private final String[] VALID_ORDERS = {"customer.id", "name", "email", "created_at", "address.id", "state", "city", "street"};

    @Autowired
    public CustomerReadRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Page<CustomerDto> findCustomers(String orderBy, String state, Pageable pageable) {

        final String finalQuery = buildSearchFinalQuery(orderBy, state);

        List<CustomerDto> customers = jdbcTemplate.query(
                finalQuery.toString(),
                new Object[]{pageable.getPageSize(), pageable.getOffset()},
                new CustomerDtoMapper()
        );

        Integer pagesN = jdbcTemplate.queryForObject(COUNT_TOTAL, Integer.class);

        return new PageImpl<>(customers, pageable, pagesN);
    }

    public Page<CustomerHistoryDto> findCustomersHistory(Long customerId, Pageable pageable) {
        List<CustomerHistoryDto> customers = jdbcTemplate.query(
                FIND_CUSTOMERS_HISTORY + PAGEABLE,
                new Object[]{customerId, pageable.getPageSize(), pageable.getOffset()},
                new CustomerHistoryMapper()
        );

        Integer pagesN = jdbcTemplate.queryForObject(COUNT_TOTAL, Integer.class);

        return new PageImpl<>(customers, pageable, pagesN);
    }
    @SneakyThrows
    private String buildSearchFinalQuery(String orderBy, String state){
        StringBuilder finalQuery = new StringBuilder(FIND_CUSTOMERS);

        if(state != null && !state.isEmpty() && AddressUtil.isAValidState(state)){
            finalQuery.append(WHERE_STATE.replace("?", state));
        }
        if(Arrays.asList(VALID_ORDERS).contains(orderBy.toLowerCase())){
            finalQuery.append(ORDER_BY.replace("?", orderBy));
        }else {
            throw new SQLSyntaxErrorException("Unknown column in 'order clause'");
        }
        finalQuery.append(PAGEABLE);
        return finalQuery.toString();
    }
}
