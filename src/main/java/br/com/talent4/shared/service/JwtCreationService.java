package br.com.talent4.shared.service;

import br.com.talent4.user.domain.User;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtCreationService extends JwtService{

    private final JwtValidationService validationService;

    @Autowired
    public JwtCreationService(JwtValidationService validationService) {
        this.validationService = validationService;
    }

    public Map<String, String> createUserTokens(User user){
        return createUserTokens(user, createRefreshJwt(user));
    }

    private Map<String, String> createUserTokens(User user, String refreshToken){
        Map<String, String> tokens = new HashMap<>();
        tokens.put("token", createJwtToken(user));
        tokens.put("refreshToken", refreshToken);
        return tokens;
    }

    private String createJwtToken(User user){
        return createJwt(String.valueOf(user.getId()),
                createJwtClaims(user),
                USER_EXPIRATION_TIME);
    }
    private String createRefreshJwt(User user){
        return createJwt(String.valueOf(user.getId()),
                createRefreshClaims(user),
                REFRESH_EXPIRATION_TIME);
    }

    private Map<String, String> createDefaultClaims(User user){
        Map<String, String> claims = new HashMap<>();
        claims.put("name", user.getName());
        claims.put("email", user.getEmail());
        return claims;
    }

    private Map<String, String> createJwtClaims(User user){
        Map<String, String> claims = createDefaultClaims(user);
        claims.put("is_refresh", "false");
        return claims;
    }
    private Map<String, String> createRefreshClaims(User user){
        Map<String, String> claims = createDefaultClaims(user);
        claims.put("is_refresh", "true");
        return claims;
    }

    private String createJwt(String subject, Map<String, String> claims, Long expirationTime){
        return Jwts.builder()
                .signWith(getSigningKey())
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .compact();
    }

    public Map<String, String> refreshToken(String refreshJwt, User user){
        validationService.validateRefreshToken(refreshJwt);
        return createUserTokens(user, refreshJwt);
    }

}
