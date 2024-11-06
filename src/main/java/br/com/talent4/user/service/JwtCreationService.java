package br.com.talent4.user.service;

import br.com.talent4.user.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtCreationService {
    @Value("${jwt.secret}")
    private String secret;
    private static final long USER_EXPIRATION_TIME = 1000L * 60 * 15; // 15 minutes
    private static final long REFRESH_EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 15; // 15 days


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

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
