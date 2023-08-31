package com.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.Role;
import com.app.service.RoleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RoleController {

	private final RoleService roleService;
	
	@GetMapping("/roles")
	public ResponseEntity<List<Role>> getAllUser() {
		return ResponseEntity.ok().body(roleService.getALlRoles());
	}
}
