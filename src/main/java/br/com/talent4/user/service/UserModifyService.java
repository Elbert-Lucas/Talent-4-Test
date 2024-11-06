package br.com.talent4.user.service;

import br.com.talent4.shared.dto.CreatedMessageResponseDto;
import br.com.talent4.user.controller.dto.RegisterUserDTO;
import br.com.talent4.user.exception.InvalidConfirmPasswordException;
import br.com.talent4.user.repository.UserModifyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserModifyService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserModifyRepository repository;

    @Autowired
    public UserModifyService(BCryptPasswordEncoder passwordEncoder, UserModifyRepository repository) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    public CreatedMessageResponseDto registerUser(RegisterUserDTO registerUserDTO) {
        if (!registerUserDTO.getPassword().equals(registerUserDTO.getConfirmPassword())) throw new InvalidConfirmPasswordException();
        else registerUserDTO.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));

        Long createdId = repository.saveUser(registerUserDTO);
        return new CreatedMessageResponseDto("Usuario criado com sucesso", createdId);
    }
}
