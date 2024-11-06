package br.com.talent4.user.controller;

import br.com.talent4.shared.dto.CreatedMessageResponseDto;
import br.com.talent4.user.controller.dto.RegisterUserDTO;
import br.com.talent4.user.service.UserModifyService;
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
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserModifyService userModifyService;

    @Autowired
    public UserController(UserModifyService userModifyService) {
        this.userModifyService = userModifyService;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedMessageResponseDto> registerUser(@RequestBody @Valid RegisterUserDTO registerUserDTO){
        log.info("Inciando a criação de usuario. Nome: "+ registerUserDTO.getName() + "; Email: " + registerUserDTO.getEmail());
        return new ResponseEntity<>(userModifyService.registerUser(registerUserDTO), HttpStatus.CREATED);
    }
}
