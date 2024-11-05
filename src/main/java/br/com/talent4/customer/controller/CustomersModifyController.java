package br.com.talent4.customer.controller;

import br.com.talent4.customer.dto.CustomerDto;
import br.com.talent4.customer.service.CustomerModifyService;
import br.com.talent4.shared.dto.CreatedMessageResponseDto;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@Slf4j
public class CustomersModifyController {

    private final CustomerModifyService service;

    @Autowired
    public CustomersModifyController(CustomerModifyService service) {
        this.service = service;
    }


    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CreatedMessageResponseDto> createCustomer(@JsonView(CustomerDto.CreateCustomerView.class)
                                                             @RequestBody @Valid CustomerDto customerDto){
        log.info("Iniciando criação de cliente! Nome: " + customerDto.getName() + "; Email: " + customerDto.getEmail());
        return new ResponseEntity<>(service.createCustomer(customerDto), HttpStatus.CREATED);
    };

    @PutMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> editCustomer(@RequestHeader("customer-id") long customerId,
                                    @JsonView(CustomerDto.CreateCustomerView.class) @RequestBody @Valid CustomerDto customerDto){
        log.info("Iniciando edição de cliente! Id: " + customerId);
        return new ResponseEntity<>(service.editCustomer(customerId, customerDto), HttpStatus.OK);
    };
}
