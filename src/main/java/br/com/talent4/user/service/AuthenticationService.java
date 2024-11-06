package br.com.talent4.user.service;

import br.com.talent4.shared.util.MessageUtil;
import br.com.talent4.user.controller.dto.CredentialsDto;
import br.com.talent4.user.controller.dto.LoginDTO;
import br.com.talent4.user.domain.User;
import br.com.talent4.user.exception.InvalidUsernameOrPasswordException;
import br.com.talent4.user.repository.UserReadRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationService {

    private final JwtCreationService jwtCreationService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MessageUtil messageUtil;

    private final UserReadRepository userReadRepository;

    @Autowired
    public AuthenticationService(JwtCreationService jwtCreationService, BCryptPasswordEncoder passwordEncoder, MessageUtil messageUtil, UserReadRepository userReadRepository) {
        this.jwtCreationService = jwtCreationService;
        this.passwordEncoder = passwordEncoder;
        this.messageUtil = messageUtil;
        this.userReadRepository = userReadRepository;
    }

    public CredentialsDto doLogin(LoginDTO loginDTO) {
        User user = userReadRepository.findUser(loginDTO.getEmail());
        validatePassword(loginDTO.getPassword(), user);
        log.info("Usuario logado! Email: " + loginDTO.getEmail());
        return new CredentialsDto(jwtCreationService.createUserTokens(user));
    }


    private void validatePassword(String passwordDTO, User user){
        if (!passwordEncoder.matches(passwordDTO, user.getPassword())) throw new InvalidUsernameOrPasswordException(messageUtil.getMessage("invalid.login"));
    }
}
