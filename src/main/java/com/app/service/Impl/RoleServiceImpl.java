package com.app.service.Impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.app.entity.Role;
import com.app.repository.RoleRepository;
import com.app.service.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

	private final RoleRepository roleRepository;
	
	
	
//	public RoleServiceImpl(RoleRepository roleRepository) {
//		this.roleRepository = roleRepository;
//	}

	@Override
	public List<Role> getALlRoles() {
		return roleRepository.findAll();
	}
	
	@Override
	public Role getRoleByName(String name) {
		return roleRepository.findByName(name);
	}
	
	@Override
	public Role saveRole(String roleName) {
		Role role = new Role(null,roleName,false);
		return roleRepository.save(role);
	}

	
}
