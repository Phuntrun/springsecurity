package com.trgphun.springsecurity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trgphun.springsecurity.dto.request.RoleRequest;
import com.trgphun.springsecurity.service.RoleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RoleRequest request) {
        return ResponseEntity.ok(roleService.create(request));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        return ResponseEntity.ok(roleService.findById(name));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody RoleRequest request) {
        return ResponseEntity.ok(roleService.update(request));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> delete(@PathVariable String name) {
        roleService.delete(name);
        return ResponseEntity.ok("Role successfully deleted!");
    }
}
