package com.wantfood.aplication.api.controller;

import com.wantfood.aplication.api.assembler.PermissionDTOAssembler;
import com.wantfood.aplication.api.model.PermissionDTO;
import com.wantfood.aplication.domain.service.ServiceGroupRegistration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/groups/{groupId}/permissoes")
@RequiredArgsConstructor
public class GroupPermissionController {
	
	private final ServiceGroupRegistration serviceGroupRegistration;
	
	private final PermissionDTOAssembler permissionDTOAssembler;
	
	@GetMapping
	public List<PermissionDTO> list(@PathVariable Long groupId) {
		var group = serviceGroupRegistration.fetchOrFail(groupId);
		
		return permissionDTOAssembler.toCollectionModel(group.getPermissoes());
	}
	
	@DeleteMapping("/{permissionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disassociate(@PathVariable Long groupId, @PathVariable Long permissionId) {
		serviceGroupRegistration.disassociatePermission(groupId, permissionId);
	}
	
	@PutMapping("/{permissionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void connect(@PathVariable Long groupId, @PathVariable Long permissionId) {
		serviceGroupRegistration.connectPermission(groupId, permissionId);
	}
	
}
