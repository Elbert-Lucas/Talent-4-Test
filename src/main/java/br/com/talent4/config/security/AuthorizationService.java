package br.com.talent4.config.security;

import br.com.talent4.shared.service.JwtValidationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@Slf4j
public class AuthorizationService {

    private final JwtValidationService jwtValidationService;

    @Autowired
    public AuthorizationService(JwtValidationService jwtValidationService) {
        this.jwtValidationService = jwtValidationService;
    }

    public UsernamePasswordAuthenticationToken authenticate(String jwt){
        log.debug("Autenticando usuario");

        jwtValidationService.validateToken(jwt);
        Long userId = Long.parseLong(jwtValidationService.getJwtSub(jwt), 10);

        log.debug("Usuario autenticado! Id: " + userId);
        saveUserOnRequestContext(userId);

        return new UsernamePasswordAuthenticationToken(
                userId,
            null,
            null
        );
    }
    private void saveUserOnRequestContext(Long userId){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        request.setAttribute("user", userId);
    }
}
