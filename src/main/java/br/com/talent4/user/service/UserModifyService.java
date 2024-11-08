package br.com.talent4.user.service;

import br.com.talent4.shared.dto.CreatedMessageResponseDto;
import br.com.talent4.shared.util.MessageUtil;
import br.com.talent4.user.dto.RegisterUserDTO;
import br.com.talent4.user.exception.InvalidConfirmPasswordException;
import br.com.talent4.user.repository.UserModifyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserModifyService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserModifyRepository repository;
    private final MessageUtil messageUtil;

    @Autowired
    public UserModifyService(BCryptPasswordEncoder passwordEncoder, UserModifyRepository repository, MessageUtil messageUtil) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
        this.messageUtil = messageUtil;
    }

    public CreatedMessageResponseDto registerUser(RegisterUserDTO registerUserDTO) {
        if (!registerUserDTO.getPassword().equals(registerUserDTO.getConfirmPassword())) throw new InvalidConfirmPasswordException(messageUtil.getMessage("invalid.confirmPassword"));
        else registerUserDTO.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));

        Long createdId = repository.saveUser(registerUserDTO);
        return new CreatedMessageResponseDto("Usuario criado com sucesso", createdId);
    }
}
