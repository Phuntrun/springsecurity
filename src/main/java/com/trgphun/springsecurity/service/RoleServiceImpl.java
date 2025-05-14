package com.trgphun.springsecurity.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.trgphun.springsecurity.dto.request.RoleRequest;
import com.trgphun.springsecurity.dto.response.RoleResponse;
import com.trgphun.springsecurity.enums.ErrorCode;
import com.trgphun.springsecurity.exception.AppException;
import com.trgphun.springsecurity.mapper.RoleMapper;
import com.trgphun.springsecurity.model.Role;
import com.trgphun.springsecurity.repository.PermissionRepository;
import com.trgphun.springsecurity.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final PermissionRepository permissionRepository;
    
    @Override
    public String create(RoleRequest request) {
        Role role = roleMapper.toRole(request);
        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        roleRepository.save(role);
        return "Role created successfully";
    }

    @Override
    public List<RoleResponse> findAll() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toRoleResponse).toList();
    }

    @Override
    public RoleResponse findById(String name) {
        return roleRepository.findById(name)
                .map(roleMapper::toRoleResponse)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
    }

    @Override
    public String update(RoleRequest request) {
        Role role = roleRepository.findById(request.getName()).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        roleRepository.save(role);
        return "Role created successfully";
        
    }

    @Override
    public void delete(String name) {
        roleRepository.deleteById(name);
    }

}
