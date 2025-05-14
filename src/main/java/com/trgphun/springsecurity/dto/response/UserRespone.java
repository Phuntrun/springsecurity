package com.trgphun.springsecurity.dto.response;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRespone {
    private String id;
    private String username;
    private String name;

    private Set<RoleResponse> roles;
}
