package com.wantfood.aplication.domain.model;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class User {
	
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String password;
	 
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dateRegister;
	
	@ManyToMany
	@JoinTable(name = "user_group", joinColumns = @JoinColumn( name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "group_id"))
	private Set<Group> groups = new HashSet<>();
	
	public boolean passwordCoincideCom(String password) {
	    return getPassword().equals(password);
	}

	public boolean passwordNaoCoincideCom(String password) {
	    return !passwordCoincideCom(password);
	}
	
	public boolean removeGroup(Group group) {
		return getGroups().remove(group);
	}
	
	public boolean addGroup(Group group) {
		return getGroups().add(group);
	}
}
