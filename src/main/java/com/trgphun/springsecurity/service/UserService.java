package com.trgphun.springsecurity.service;

import java.util.List;

import com.trgphun.springsecurity.dto.request.UserRequest;
import com.trgphun.springsecurity.dto.response.UserRespone;

public interface UserService {
    String create(UserRequest request);
    List<UserRespone> getAllUsers();
    UserRespone getUserById(String id);
    String updateUser(String id, UserRequest request);
    void deleteUser(String id);
}
