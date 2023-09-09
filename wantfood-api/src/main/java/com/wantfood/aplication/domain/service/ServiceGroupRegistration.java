package com.wantfood.aplication.domain.service;

import com.wantfood.aplication.domain.constants.Messages;
import com.wantfood.aplication.domain.exception.EntityInUseException;
import com.wantfood.aplication.domain.exception.GroupNotFoundException;
import com.wantfood.aplication.domain.exception.StateNotFoundException;
import com.wantfood.aplication.domain.model.Group;
import com.wantfood.aplication.domain.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ServiceGroupRegistration {
	
	private final GroupRepository groupRepository;
	
	private final RegistrationPermissionService registrationPermissionService;
	
	@Transactional
	public Group add(Group group) {
		return groupRepository.save(group);
	}
		
	@Transactional
	public void delete(Long groupId) {
		try {
			groupRepository.deleteById(groupId);
			groupRepository.flush();
		}catch(EmptyResultDataAccessException e) {
			throw  new StateNotFoundException(groupId);
		}catch(DataIntegrityViolationException e) {
			throw new EntityInUseException(Messages.GROUP_IN_USE);
		}
	}
	
	public Group fetchOrFail(Long groupId) {
		return groupRepository.findById(groupId)
				.orElseThrow(() -> new GroupNotFoundException(groupId));
	}
	
	@Transactional
	public void disassociatePermission(Long groupId, Long permissionId) {
		var group = fetchOrFail(groupId);
		var permission = registrationPermissionService.fetchOrFail(permissionId);
		
		group.removePemissao(permission);
	}
	
	@Transactional
	public void connectPermission(Long groupId, Long permissionId) {
		var group = fetchOrFail(groupId);
		var permission = registrationPermissionService.fetchOrFail(permissionId);
		
		group.adicionaPemissao(permission);
	}
}
