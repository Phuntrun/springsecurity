package com.trgphun.springsecurity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.trgphun.springsecurity.dto.request.RoleRequest;
import com.trgphun.springsecurity.dto.response.RoleResponse;
import com.trgphun.springsecurity.model.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request); 
    RoleResponse toRoleResponse(Role role);
}
