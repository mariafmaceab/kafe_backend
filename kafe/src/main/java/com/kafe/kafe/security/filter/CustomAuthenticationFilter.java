package com.kafe.kafe.security.filter;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kafe.kafe.dto.request.LoginForm;
import com.kafe.kafe.security.util.TokenCreator;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        String username = null;
        String password = null;
        LoginForm userCredentials;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String body = request.getReader().lines().collect(Collectors.joining());
            userCredentials = objectMapper.readValue(body, LoginForm.class);
            username = userCredentials.getUsername();
            password = userCredentials.getPassword();
        } catch (IOException e) {
            logger.error(e);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        String subject = user.getUsername();
        Date accessTokenExpiration = new Date(System.currentTimeMillis() + 10 * 60 * 1000);
        Date refreshTokenExpiration = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
        String issuer = request.getRequestURI();
        String claim = "roles";
        List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        String accessToken = TokenCreator.createNewToken(subject, accessTokenExpiration, issuer, claim,
                roles, algorithm);
        String refreshToken = TokenCreator.createNewToken(subject, refreshTokenExpiration, issuer, claim,
                roles, algorithm);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access", accessToken);
        tokens.put("refresh", refreshToken);
        tokens.put("username", subject);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader("Access-Control-Allow-Origin","*");
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

}
