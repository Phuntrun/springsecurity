package com.trgphun.springsecurity.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.trgphun.springsecurity.dto.request.IntrospectRequest;
import com.trgphun.springsecurity.dto.request.LoginRequest;
import com.trgphun.springsecurity.dto.request.LogoutRequest;
import com.trgphun.springsecurity.dto.request.RefreshRequest;
import com.trgphun.springsecurity.dto.response.IntrospectResponse;
import com.trgphun.springsecurity.dto.response.LoginResponse;
import com.trgphun.springsecurity.enums.ErrorCode;
import com.trgphun.springsecurity.exception.AppException;
import com.trgphun.springsecurity.model.InvalidToken;
import com.trgphun.springsecurity.model.User;
import com.trgphun.springsecurity.repository.InvalidTokenRepository;
import com.trgphun.springsecurity.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final InvalidTokenRepository invalidTokenRepository;

    @NonFinal
    @Value("${spring.jwt.signkey}")
    protected String signKey;

    @NonFinal
    @Value("${spring.jwt.valid-duration}")
    protected long validationDuration;

    @NonFinal
    @Value("${spring.jwt.refreshable-duration}")
    protected long refreshableDuration;
    
    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_UNREGISTERED));
        
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isPasswordMatch = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!isPasswordMatch) throw new AppException(ErrorCode.WRONG_PASSWORD);
        String token = generateToken(user);
                
        return LoginResponse.builder()
                .success(true)
                .token(token)
                .build();
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        boolean valid = true;
        try { verifyToken(request.getToken(), false);}
        catch (AppException e) {valid = false;}

        return IntrospectResponse.builder().valid(valid).build();
    }

    @Override
    public String logout(LogoutRequest request) {
        try {
            var signedJWT = verifyToken(request.getToken(), true);

            String jit = signedJWT.getJWTClaimsSet().getJWTID();
            Date expiry = signedJWT.getJWTClaimsSet().getExpirationTime();

            invalidTokenRepository.save(new InvalidToken(jit, expiry));
            return "Logout successfully!";
        } catch (AppException | ParseException | JOSEException e) {
            return "You weren't logged in!";
        }
    }

    @Override
    public LoginResponse refresh(RefreshRequest request) throws ParseException, JOSEException {
        var signedToken = verifyToken(request.getToken(), true);

        var id = signedToken.getJWTClaimsSet().getJWTID();
        var expiry = signedToken.getJWTClaimsSet().getExpirationTime();

        InvalidToken invalidToken = InvalidToken.builder().id(id).expiry(expiry).build();

        invalidTokenRepository.save(invalidToken);

        var username = signedToken.getJWTClaimsSet().getSubject();
        var user =
                userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        return LoginResponse.builder()
                .success(true)
                .token(generateToken(user))
                .build();
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(signKey.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT
                .getJWTClaimsSet()
                .getIssueTime()
                .toInstant()
                .plus(refreshableDuration, ChronoUnit.SECONDS)
                .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);

        if (invalidTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("PhunTrun")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now()
                        .plus(validationDuration, ChronoUnit.SECONDS)
                        .toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jWSObject = new JWSObject(header, payload);
        try {
            jWSObject.sign(new MACSigner(signKey.getBytes()));
            return jWSObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions()))
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
            });

        return stringJoiner.toString();
    }

}
