package br.com.talent4.customer.controller;

import br.com.talent4.customer.dto.CustomerDto;
import br.com.talent4.customer.service.CustomerModifyService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
@Slf4j
public class CustomersModifyController {

    private final CustomerModifyService service;

    @Autowired
    public CustomersModifyController(CustomerModifyService service) {
        this.service = service;
    }

    @JsonView(CustomerDto.CreateCustomerView.class)
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> createCustomer(@RequestBody @Valid CustomerDto customerDto){
        log.debug("Iniciando criação de cliente");
        service.createCustomer(customerDto);
        return new ResponseEntity<>(HttpStatus.OK);
    };
}
