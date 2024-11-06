package br.com.talent4.customer.controller.swagger;

import br.com.talent4.customer.dto.CustomerDto;
import br.com.talent4.customer.dto.CustomerHistoryDto;
import br.com.talent4.shared.dto.CreatedMessageResponseDto;
import br.com.talent4.shared.dto.DeletedMessageResponse;
import br.com.talent4.shared.dto.EditedMessageResponseDto;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLSyntaxErrorException;

public interface CustomersControllerSwagger {

    @GetMapping(value = "")
    @SecurityRequirement(name = "Authorization")
    @Operation(description = "Busca por todos os clientes. É possivel filtrar por estados, ordenar por diferentes parametros e paginar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os clientes"),
            @ApiResponse(responseCode = "401", description = "Ocorre ao enviar um JWT invalido"),
            @ApiResponse(responseCode = "500", description = "Ocorre ao nao enviar um JWT ou com a formatação errada")
    })
    ResponseEntity<Page<CustomerDto>> findCustomers(@RequestParam(name = "orderBy", defaultValue = "customer.name") String orderBy,
                                                    @RequestParam(required = false) String state,
                                                    Pageable pageable) throws SQLSyntaxErrorException;

    @GetMapping(value = "/history/{id}")
    @SecurityRequirement(name = "Authorization")
    @Operation(description = "Busca pelo historico de um cliente especifico. É possivel paginar.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o historico"),
            @ApiResponse(responseCode = "401", description = "Ocorre ao enviar um JWT invalido"),
            @ApiResponse(responseCode = "500", description = "Ocorre ao nao enviar um JWT ou com a formatação errada")
    })
    ResponseEntity<Page<CustomerHistoryDto>> findCustomerHistory(@PathVariable("id") long customerId,
                                                                 Pageable pageable);


    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "Authorization")
    @Operation(description = "Cria um novo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna mensagem de sucesso e o id do novo cliente"),
            @ApiResponse(responseCode = "400", description = "Ocorre ao enviar alguma informação errada"),
            @ApiResponse(responseCode = "401", description = "Ocorre ao enviar um JWT invalido"),
            @ApiResponse(responseCode = "500", description = "Ocorre ao nao enviar um JWT ou com a formatação errada")
    })
    ResponseEntity<CreatedMessageResponseDto> createCustomer(@JsonView(CustomerDto.CreateCustomerView.class)
                                                             @RequestBody @Valid CustomerDto customerDto);

    @PutMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "Authorization")
    @Operation(description = "Edita um usuario já existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna mensagem de sucesso"),
            @ApiResponse(responseCode = "400", description = "Ocorre ao enviar alguma informação errada"),
            @ApiResponse(responseCode = "401", description = "Ocorre ao enviar um JWT invalido"),
            @ApiResponse(responseCode = "404", description = "Ocorre quando o id do cliente não existe no banco de dados"),
            @ApiResponse(responseCode = "500", description = "Ocorre ao nao enviar um JWT ou com a formatação errada")
    })
    ResponseEntity<EditedMessageResponseDto> editCustomer(@RequestHeader("customer-id") long customerId,
                                                          @JsonView(CustomerDto.CreateCustomerView.class) @RequestBody @Valid CustomerDto customerDto);

    @DeleteMapping(value = "/delete/{id}")
    @SecurityRequirement(name = "Authorization")
    @Operation(description = "Deleta um usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna mensagem de sucesso"),
            @ApiResponse(responseCode = "400", description = "Ocorre ao enviar alguma informação errada"),
            @ApiResponse(responseCode = "401", description = "Ocorre ao enviar um JWT invalido"),
            @ApiResponse(responseCode = "404", description = "Ocorre quando o id do cliente não existe no banco de dados"),
            @ApiResponse(responseCode = "500", description = "Ocorre ao nao enviar um JWT ou com a formatação errada")
    })
    ResponseEntity<DeletedMessageResponse> deleteCustomer(@PathVariable("id") long customerId);
}
