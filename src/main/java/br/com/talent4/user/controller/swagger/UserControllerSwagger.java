package br.com.talent4.user.controller.swagger;

import br.com.talent4.shared.dto.CreatedMessageResponseDto;
import br.com.talent4.shared.dto.RefreshTokenDTO;
import br.com.talent4.shared.dto.dto.CredentialsDto;
import br.com.talent4.shared.dto.dto.LoginDTO;
import br.com.talent4.shared.dto.dto.RegisterUserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserControllerSwagger {

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Registra um usuario da aplicação, capaz de realizar ações com os clientes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna mensagem de sucesso e id do usuario criado"),
            @ApiResponse(responseCode = "400", description = "Ocorre ao enviar alguma informação errada")
    })
    public ResponseEntity<CreatedMessageResponseDto> registerUser(@RequestBody @Valid RegisterUserDTO registerUserDTO);

    @PostMapping("/login")
    @Operation(description = "Realiza o login do usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna o JWT de autenticação e o refresh token"),
            @ApiResponse(responseCode = "400", description = "Ocorre ao enviar alguma informação errada"),
            @ApiResponse(responseCode = "401", description = "Ocorre quando a senha estao incorretos"),
            @ApiResponse(responseCode = "404", description = "Ocorre quando o email do usuario não existe no banco de dados")
    })
    public ResponseEntity<CredentialsDto> login(@RequestBody @Valid LoginDTO loginDTO);

    @PostMapping("/refresh-token")
    @Operation(description = "Faz o refresh do token para continuar o acesso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna o JWT de autenticação e o refresh token"),
            @ApiResponse(responseCode = "400", description = "Ocorre ao enviar alguma informação errada"),
            @ApiResponse(responseCode = "401", description = "Ocorre quando o refresh token é invalido"),
            @ApiResponse(responseCode = "500", description = "Ocorre ao enviar um JWT com a formatação errada")
    })
    public ResponseEntity<CredentialsDto> refreshToken(@RequestBody RefreshTokenDTO refreshToken);
}
