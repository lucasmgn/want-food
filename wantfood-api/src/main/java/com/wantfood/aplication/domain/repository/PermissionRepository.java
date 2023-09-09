package com.wantfood.aplication.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wantfood.aplication.domain.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long>{

}	
