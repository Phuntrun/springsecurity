package com.trgphun.springsecurity.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.trgphun.springsecurity.dto.request.UserRequest;
import com.trgphun.springsecurity.dto.response.UserRespone;
import com.trgphun.springsecurity.mapper.UserMapper;
import com.trgphun.springsecurity.model.User;
import com.trgphun.springsecurity.repository.RoleRepository;
import com.trgphun.springsecurity.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Override
    public String create(UserRequest request) {
        User user = userMapper.toUser(request);
        user.setRoles(new HashSet<>(roleRepository.findAllById(request.getRoles())));
        userRepository.save(user);
        return "User successfully created!";
    }

    @Override
    public List<UserRespone> getAllUsers() {

        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Override
    public UserRespone getUserById(String id) {
        return userRepository.findById(id).map(userMapper::toUserResponse)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public String updateUser(String id, UserRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found!"));
        user = userMapper.toUser(request);
        user.setRoles(new HashSet<>(roleRepository.findAllById(request.getRoles())));
        userRepository.save(user);
        return "User successfully updated!";
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
    
}
