package com.trgphun.springsecurity.service;

import java.util.List;
import com.trgphun.springsecurity.dto.request.PermissionRequest;
import com.trgphun.springsecurity.dto.response.PermissionResponse;

public interface PermissionService {
    String create(PermissionRequest request);
    List<PermissionResponse> findAll();
    PermissionResponse findById(String name);
    String update(PermissionRequest request);
    void delete(String name);
}
