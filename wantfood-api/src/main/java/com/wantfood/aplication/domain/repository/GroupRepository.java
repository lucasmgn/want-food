package com.wantfood.aplication.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wantfood.aplication.domain.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long>{

}
