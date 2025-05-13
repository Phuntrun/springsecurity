package com.trgphun.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trgphun.springsecurity.model.Role;

public interface RoleRepository extends JpaRepository<Role, String>{

}
