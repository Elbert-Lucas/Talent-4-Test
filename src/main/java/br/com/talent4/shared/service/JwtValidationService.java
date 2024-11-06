package br.com.talent4.shared.service;

import br.com.talent4.shared.util.MessageUtil;
import br.com.talent4.shared.exception.InvalidTokenException;
import br.com.talent4.user.repository.UserReadRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtValidationService extends JwtService{

    private final UserReadRepository userReadRepository;
    private final MessageUtil messageUtil;

    @Autowired
    public JwtValidationService(MessageUtil messageUtil, UserReadRepository userReadRepository) {
        this.messageUtil = messageUtil;
        this.userReadRepository = userReadRepository;
    }

    private boolean defaultIsJwtValid(String jwt){
        try {
            userReadRepository.findUserByEmailOrId(getJwtSub(jwt));
        }catch (Exception e){
            return false;
        }
        return !isTokenExpired(jwt);
    }
    public void validateToken(String jwt){
        if(!defaultIsJwtValid(jwt) || getClaimItem(jwt,"is_refresh").equals("true")) throwTokenException();
    }
    public void validateRefreshToken(String jwt){
        if(!defaultIsJwtValid(jwt) || getClaimItem(jwt,"is_refresh").equals("false")) throwTokenException();
    }

    private Claims getClaims(String jwt){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    public String getJwtSub(String jwt) {
        return getClaimItem(jwt, "sub");
    }

    private String getClaimItem(String jwt, String item) {
        return String.valueOf(getClaims(jwt).get(item));
    }

    private boolean isTokenExpired(String jwt) {
        try{
            return new Date(Long.valueOf(getClaimItem(jwt, "exp")))
                       .after(new Date());
        }catch (ExpiredJwtException e){
            return true;
        }
    }
    private void throwTokenException(){
        throw new InvalidTokenException(messageUtil.getMessage("invalid.token"));
    }

}
