package com.trgphun.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trgphun.springsecurity.model.InvalidToken;

public interface InvalidTokenRepository extends JpaRepository<InvalidToken, String> {

}
