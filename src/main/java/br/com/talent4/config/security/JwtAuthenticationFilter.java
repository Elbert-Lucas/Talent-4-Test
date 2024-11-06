package br.com.talent4.config.security;


import br.com.talent4.shared.exception.InvalidTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final List<String> WHITELIST =  List.of("/user/login",
                                                           "/user/register",
                                                           "/user/refresh-token",
                                                            "/v2/api-docs",
                                                            "/swagger-resources",
                                                            "/swagger-resources/**",
                                                            "/configuration/ui",
                                                            "/configuration/security",
                                                            "/swagger-ui.html",
                                                            "/webjars/**",
                                                            "/v3/api-docs",
                                                            "/v3/api-docs/**",
                                                            "/swagger-ui/");

    private final HandlerExceptionResolver handlerExceptionResolver;
    private final AuthorizationService authorizationService;

    @Autowired
    public JwtAuthenticationFilter(HandlerExceptionResolver handlerExceptionResolver, AuthorizationService authorizationService) {
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.authorizationService = authorizationService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if(WHITELIST.stream().anyMatch(request.getRequestURI()::contains)){
            filterChain.doFilter(request, response);
            return;
        }

        if(request.getHeader("Authorization") == null){
            throw new InvalidTokenException("Token de autorização nulo");
        }

        String jwt = request.getHeader("Authorization").substring(7);
        try {
            UsernamePasswordAuthenticationToken authentication = authorizationService.authenticate(jwt);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}
