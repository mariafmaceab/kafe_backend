package com.kafe.kafe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafe.kafe.dto.request.RoleToUserForm;
import com.kafe.kafe.dto.response.UserResponseDTO;
import com.kafe.kafe.entity.Role;
import com.kafe.kafe.entity.User;
import com.kafe.kafe.security.util.TokenCreator;
import com.kafe.kafe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        return new ResponseEntity<>(userService.getUser(username), HttpStatus.OK);
    }

    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
    }

    @GetMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        return new ResponseEntity<>(userService.saveRole(role), HttpStatus.OK);
    }

    @PostMapping("/role/add-to-user")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            TokenCreator tokenCreator = new TokenCreator(request, response);
            tokenCreator.setUserService(userService);

            try {
                String token = tokenCreator.buildToken();
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), Map.of("access", token));
            } catch (Exception e) {
                tokenCreator.tokenExceptionCreator(e);
            }

        } else {
            throw new RuntimeException("Refresh Token is missing");
        }
    }

}
