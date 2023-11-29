package com.express.security.controller;

import com.express.security.dto.CommonResponse;
import com.express.security.dto.RoleToUserRequest;
import com.express.security.entity.Role;
import com.express.security.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleService roleService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SUPERVISOR')")
    @PostMapping
    public ResponseEntity<?> saveRole(@RequestBody Role request){
        CommonResponse<?> response = CommonResponse.builder()
                .data(roleService.saveRole(request))
                .statusCode(CREATED)
                .build();
        return ResponseEntity.status(CREATED).body(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SUPERVISOR')")
    @PostMapping("/add-role-to-user")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserRequest request){
        roleService.addRoleToUser(request);
        CommonResponse<?> response = CommonResponse.builder()
                .data("Role added to user")
                .statusCode(CREATED)
                .build();
        return ResponseEntity.ok(response);
    }
}