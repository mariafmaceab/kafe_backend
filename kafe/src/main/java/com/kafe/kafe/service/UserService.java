package com.kafe.kafe.service;

import com.kafe.kafe.dto.response.UserResponseDTO;
import com.kafe.kafe.entity.Role;
import com.kafe.kafe.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<UserResponseDTO> getUsers();
}
