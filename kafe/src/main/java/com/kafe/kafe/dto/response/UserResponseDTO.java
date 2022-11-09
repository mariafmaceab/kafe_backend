package com.kafe.kafe.dto.response;

import java.util.Collection;

import com.kafe.kafe.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String name;
    private String username;
    private Collection<Role> roles;
}
