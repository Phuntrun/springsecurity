package com.trgphun.springsecurity.dto.request;

import java.util.Set;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    @Size(min = 6, max = 20, message = "USERNAME_VALID")
    private String username;
    @Size(min = 8, max = 20, message = "PASSWORD_VALID")
    private String password;

    private String name;

    private Set<String> roles;
}
