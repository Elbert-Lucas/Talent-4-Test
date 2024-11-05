package br.com.talent4.customer.service;

import br.com.talent4.customer.domain.Address;
import br.com.talent4.customer.domain.Customer;
import br.com.talent4.customer.dto.CustomerDto;
import br.com.talent4.customer.repository.CustomerModifyRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerModifyService {


    private final CustomerModifyRepository repository;

    @Autowired
    public CustomerModifyService(CustomerModifyRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void createCustomer(CustomerDto customerDto){
        repository.saveCustomer(customerDto);
    }
}
