package com.trgphun.springsecurity.repository;

import com.trgphun.springsecurity.model.Permission;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, String> {

}
