package com.kafe.kafe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class RoleToUserForm {
    private String username;
    private String roleName;
}
