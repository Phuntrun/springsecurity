package com.trgphun.springsecurity.service;

import java.util.List;

import com.trgphun.springsecurity.dto.request.RoleRequest;
import com.trgphun.springsecurity.dto.response.RoleResponse;

public interface RoleService {
    String create(RoleRequest request);

    List<RoleResponse> findAll();

    RoleResponse findById(String name);

    String update(RoleRequest request);

    void delete(String name);
}
