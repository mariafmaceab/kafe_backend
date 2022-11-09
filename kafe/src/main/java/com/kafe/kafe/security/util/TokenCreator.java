package com.kafe.kafe.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kafe.kafe.entity.Role;
import com.kafe.kafe.entity.User;
import com.kafe.kafe.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@Getter @Setter
public class TokenCreator {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private UserService userService;


    public TokenCreator(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public static String createNewToken(String subject, Date expiration, String issuer,
                                        String claim, List<String> roles, Algorithm algorithm) {
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(expiration)
                .withIssuer(issuer)
                .withClaim(claim, roles)
                .sign(algorithm);
    }

    public String buildToken() {
        String username = buildTokenInfo().getSubject();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        User user = userService.getUser(username);
        Date accessTokenExpiration = new Date(System.currentTimeMillis() + 10 * 60 * 1000);
        String issuer = request.getRequestURI();
        String claim = "roles";
        List<String> roles = user.getRoles().stream().map(Role::getName).toList();
        return createNewToken(username, accessTokenExpiration, issuer, claim, roles, algorithm);
    }

    public void tokenExceptionCreator(Exception e) throws IOException {
        response.setHeader("error", e.getMessage());
        response.setStatus(HttpStatus.FORBIDDEN.value());

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error_message", e.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
    }

    public DecodedJWT buildTokenInfo() {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = authorizationHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public String getUsername() {
        return buildTokenInfo().getSubject();
    }
}


