package com.trgphun.springsecurity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.trgphun.springsecurity.dto.request.PermissionRequest;
import com.trgphun.springsecurity.dto.response.PermissionResponse;
import com.trgphun.springsecurity.model.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest permissionRequest);
    PermissionResponse toPermissionResponse(Permission permission);
}
