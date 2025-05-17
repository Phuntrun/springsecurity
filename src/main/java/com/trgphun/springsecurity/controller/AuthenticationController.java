package com.trgphun.springsecurity.controller;

import java.text.ParseException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;
import com.trgphun.springsecurity.dto.request.IntrospectRequest;
import com.trgphun.springsecurity.dto.request.LoginRequest;
import com.trgphun.springsecurity.dto.request.LogoutRequest;
import com.trgphun.springsecurity.dto.request.RefreshRequest;
import com.trgphun.springsecurity.dto.response.IntrospectResponse;
import com.trgphun.springsecurity.dto.response.LoginResponse;
import com.trgphun.springsecurity.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Login", description = "authenticate and generate token")
    @PostMapping("/token")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        var result = authenticationService.login(request);
        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "Introspect", description = "check availability of token")
    @PostMapping("/introspect")
    public ResponseEntity<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "Refresh token", description = "refresh token's duration")
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refresh(request);
        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "Logout", description = "logout and disable token")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody LogoutRequest request) {
        var message = authenticationService.logout(request);
        return ResponseEntity.ok().body(message);
    }
}
