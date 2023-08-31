package com.app.service;

import java.util.List;

import com.app.entity.Role;

public interface RoleService {

	 Role getRoleByName(String name);

	 List<Role> getALlRoles();
	 
	 Role saveRole(String roleName);
}
