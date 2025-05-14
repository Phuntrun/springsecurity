package com.trgphun.springsecurity.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.trgphun.springsecurity.dto.request.PermissionRequest;
import com.trgphun.springsecurity.dto.response.PermissionResponse;
import com.trgphun.springsecurity.enums.ErrorCode;
import com.trgphun.springsecurity.exception.AppException;
import com.trgphun.springsecurity.mapper.PermissionMapper;
import com.trgphun.springsecurity.model.Permission;
import com.trgphun.springsecurity.repository.PermissionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService{
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    @Override
    public String create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permissionRepository.save(permission);
        return "Permission successfully created!";
    }

    @Override
    public List<PermissionResponse> findAll() {
        return permissionRepository.findAll().stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
    }

    @Override
    public PermissionResponse findById(String name) {
        return permissionRepository.findById(name)
                .map(permissionMapper::toPermissionResponse)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
    }

    @Override
    public String update(PermissionRequest request) {
        permissionRepository.findById(request.getName())
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
        permissionRepository.save(permissionMapper.toPermission(request));
        return "Permission successfully updated!";
    }

    @Override
    public void delete(String name) {
        Permission permission = permissionRepository.findById(name)
                .orElseThrow(() -> new RuntimeException("Permission not found!"));
        permissionRepository.delete(permission);
        
    }

}
