package br.com.talent4.customer.service;

import br.com.talent4.customer.domain.Address;
import br.com.talent4.customer.domain.Customer;
import br.com.talent4.customer.dto.CustomerDto;
import br.com.talent4.customer.repository.CustomerModifyRepository;
import br.com.talent4.shared.dto.CreatedMessageResponseDto;
import br.com.talent4.shared.dto.EditedMessageResponseDto;
import br.com.talent4.shared.dto.MessageResponseDto;
import br.com.talent4.shared.util.MessageUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class CustomerModifyService {

    private final CustomerModifyRepository repository;
    private final MessageUtil messageUtil;

    @Autowired
    public CustomerModifyService(CustomerModifyRepository repository, MessageUtil messageUtil) {
        this.repository = repository;
        this.messageUtil = messageUtil;
    }

    @Transactional
    public CreatedMessageResponseDto createCustomer(CustomerDto customerDto){
        Long id = repository.saveCustomer(customerDto);
        log.info("Usuario criado! Id: " + id);
        return new CreatedMessageResponseDto(messageUtil.getMessage("customer.created"), id);
    }

    @Transactional
    public EditedMessageResponseDto editCustomer(long customerId, CustomerDto customerDto) {
        int rowsAffected = repository.updateCustomer(customerId, customerDto);
        log.info("Usuario editado! Numero de linhas afetadas: " + rowsAffected);
        return new EditedMessageResponseDto(messageUtil.getMessage("customer.edited"));

    }
}
