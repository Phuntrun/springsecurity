package com.trgphun.springsecurity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trgphun.springsecurity.dto.request.PermissionRequest;
import com.trgphun.springsecurity.dto.response.PermissionResponse;
import com.trgphun.springsecurity.service.PermissionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
@Tag(name = "Permission", description = "Permission API")
public class PermissionController {
    private final PermissionService permissionService;

    @Operation(summary = "Create permission", description = "Create permission")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody PermissionRequest request) {
        
        return ResponseEntity.ok(permissionService.create(request));
    }

    @Operation(summary = "Get all permissions", description = "Get all permissions")
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(permissionService.findAll());
    }

    @Operation(summary = "Get permission by name", description = "Get permission by name")
    @GetMapping("/{name}")
    public ResponseEntity<PermissionResponse> findByName(@PathVariable String name) {
        return ResponseEntity.ok(permissionService.findById(name));
    }
    
    @Operation(summary = "Update permission", description = "Update permission")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody PermissionRequest request) {
        return ResponseEntity.ok(permissionService.update(request));
    }
    
    @Operation(summary = "Delete permission", description = "Delete permission")
    @DeleteMapping("/{name}")
    public ResponseEntity<?> delete(@PathVariable String name) {
        permissionService.delete(name);
        return ResponseEntity.ok("Permission successfully deleted!");
    }
}
