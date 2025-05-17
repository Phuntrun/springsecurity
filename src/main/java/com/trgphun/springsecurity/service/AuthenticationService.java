package com.trgphun.springsecurity.service;

import java.text.ParseException;

import com.nimbusds.jose.JOSEException;
import com.trgphun.springsecurity.dto.request.IntrospectRequest;
import com.trgphun.springsecurity.dto.request.LoginRequest;
import com.trgphun.springsecurity.dto.request.LogoutRequest;
import com.trgphun.springsecurity.dto.request.RefreshRequest;
import com.trgphun.springsecurity.dto.response.IntrospectResponse;
import com.trgphun.springsecurity.dto.response.LoginResponse;

public interface AuthenticationService {
    LoginResponse login(LoginRequest request);
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
    String logout(LogoutRequest request);
    LoginResponse refresh(RefreshRequest request) throws ParseException, JOSEException;
    // SendEmailResponse isSentResetLink(SendEmailRequest request);
    // ResetPasswordResponse resetPassword(ResetPasswordRequest request, String token);
}
