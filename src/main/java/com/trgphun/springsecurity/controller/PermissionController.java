package com.trgphun.springsecurity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trgphun.springsecurity.dto.request.PermissionRequest;
import com.trgphun.springsecurity.dto.response.PermissionResponse;
import com.trgphun.springsecurity.service.PermissionService;

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
public class PermissionController {
    private final PermissionService permissionService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PermissionRequest request) {
        
        return ResponseEntity.ok(permissionService.create(request));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(permissionService.findAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<PermissionResponse> findByName(@PathVariable String name) {
        return ResponseEntity.ok(permissionService.findById(name));
    }
    
    @PutMapping
    public ResponseEntity<?> update(@RequestBody PermissionRequest request) {
        return ResponseEntity.ok(permissionService.update(request));
    }
    
    @DeleteMapping("/{name}")
    public ResponseEntity<?> delete(@PathVariable String name) {
        permissionService.delete(name);
        return ResponseEntity.ok("Permission successfully deleted!");
    }
}
