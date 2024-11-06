package br.com.talent4.customer.service;

import br.com.talent4.customer.dto.CustomerDto;
import br.com.talent4.customer.dto.CustomerHistoryDto;
import br.com.talent4.customer.repository.CustomerReadRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomerReadService {

    private final CustomerReadRepository repository;

    @Autowired
    public CustomerReadService(CustomerReadRepository repository) {
        this.repository = repository;
    }

    public Page<CustomerDto> findCustomers(String orderBy, String state, Pageable pageable) {
        Page<CustomerDto> page = repository.findCustomers(orderBy, state, pageable);
        log.info("Busca por cliente finalizada. Total de registros: " + page.getTotalElements());
        return page;
    }

    public Page<CustomerHistoryDto> findCustomerHistory(long customerId, Pageable pageable) {
        Page<CustomerHistoryDto> page = repository.findCustomersHistory(customerId, pageable);
        log.info("Busca por historico finalizada. Total de registros: " + page.getTotalElements());
        return page;
    }
}
