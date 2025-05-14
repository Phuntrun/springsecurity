package com.trgphun.springsecurity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.trgphun.springsecurity.dto.request.UserRequest;
import com.trgphun.springsecurity.dto.response.UserRespone;
import com.trgphun.springsecurity.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    User toUser(UserRequest userRequest);
    UserRespone toUserResponse(User user);
}
