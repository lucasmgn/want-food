package com.wantfood.aplication.domain.service;

import com.wantfood.aplication.domain.exception.PermissionNotFoundException;
import com.wantfood.aplication.domain.model.Permission;
import com.wantfood.aplication.domain.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationPermissionService {
	
    private final PermissionRepository permissionRepository;
    
    public Permission fetchOrFail(Long permissionId) {
        return permissionRepository.findById(permissionId)
            .orElseThrow(() -> new PermissionNotFoundException(permissionId));
    }
}
