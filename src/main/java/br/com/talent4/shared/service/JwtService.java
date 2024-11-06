package br.com.talent4.shared.service;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public abstract class JwtService {

    @Value("${jwt.secret}")
    protected String secret;
    protected static final long USER_EXPIRATION_TIME = 1000L * 60 * 15; // 15 minutes
    protected static final long REFRESH_EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 15; // 15 days

    protected SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
