package br.com.talent4.customer.controller;

import br.com.talent4.customer.dto.CustomerDto;
import br.com.talent4.customer.service.CustomerModifyService;
import br.com.talent4.customer.service.CustomerReadService;
import br.com.talent4.shared.dto.CreatedMessageResponseDto;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLSyntaxErrorException;

@RestController
@RequestMapping("/customers")
@Slf4j
public class CustomersController {

    private final CustomerModifyService modifyService;
    private final CustomerReadService readService;

    @Autowired
    public CustomersController(CustomerModifyService modifyService, CustomerReadService readService) {
        this.modifyService = modifyService;
        this.readService = readService;
    }


    @GetMapping(value = "/list")
    ResponseEntity<Page<CustomerDto>> findCustomers(@RequestParam(name = "orderBy", defaultValue = "name") String orderBy,
                                                    @RequestParam(required = false) String state,
                                                    Pageable pageable) throws SQLSyntaxErrorException {
        log.info("Iniciando busca por clientes! Ordenado por: " + orderBy + ", Estado: " + state);
        return new ResponseEntity<>(readService.findCustomers(orderBy, state, pageable), HttpStatus.OK);
    }


    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CreatedMessageResponseDto> createCustomer(@JsonView(CustomerDto.CreateCustomerView.class)
                                                             @RequestBody @Valid CustomerDto customerDto){
        log.info("Iniciando criação de cliente! Nome: " + customerDto.getName() + "; Email: " + customerDto.getEmail());
        return new ResponseEntity<>(modifyService.createCustomer(customerDto), HttpStatus.CREATED);
    };

    @PutMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> editCustomer(@RequestHeader("customer-id") long customerId,
                                    @JsonView(CustomerDto.CreateCustomerView.class) @RequestBody @Valid CustomerDto customerDto){
        log.info("Iniciando edição de cliente! Id: " + customerId);
        return new ResponseEntity<>(modifyService.editCustomer(customerId, customerDto), HttpStatus.OK);
    };

}
