package br.com.talent4.customer.service;

import br.com.talent4.customer.dto.CustomerDto;
import br.com.talent4.customer.repository.CustomerModifyRepository;
import br.com.talent4.shared.dto.CreatedMessageResponseDto;
import br.com.talent4.shared.dto.DeletedMessageResponse;
import br.com.talent4.shared.dto.EditedMessageResponseDto;
import br.com.talent4.shared.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

    @Transactional
    public DeletedMessageResponse deleteCustomer(long customerId) {
        int rowsAffected = repository.deleteCustomer(customerId);
        if(rowsAffected == 0) throw new EmptyResultDataAccessException(1);
        log.info("Usuario deletado! Numero de linhas afetadas: " + rowsAffected);
        return new DeletedMessageResponse(messageUtil.getMessage("customer.deleted"));
    }
}
