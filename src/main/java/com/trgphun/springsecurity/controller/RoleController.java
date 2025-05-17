package com.trgphun.springsecurity.controller;

import org.springframework.http.MediaType;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Tag(name = "Role", description = "Role API")
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "Create role", description = "Create role")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody RoleRequest request) {
        return ResponseEntity.ok(roleService.create(request));
    }

    @Operation(summary = "Get all roles", description = "Get all roles")
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @Operation(summary = "Get role by name", description = "Get role by name")
    @GetMapping("/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        return ResponseEntity.ok(roleService.findById(name));
    }

    @Operation(summary = "Update role", description = "Update role")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody RoleRequest request) {
        return ResponseEntity.ok(roleService.update(request));
    }

    @Operation(summary = "Delete role", description = "Delete role")
    @DeleteMapping("/{name}")
    public ResponseEntity<?> delete(@PathVariable String name) {
        roleService.delete(name);
        return ResponseEntity.ok("Role successfully deleted!");
    }
}
