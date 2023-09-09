package com.wantfood.aplication.domain.service;

import com.wantfood.aplication.domain.exception.BusinessException;
import com.wantfood.aplication.domain.exception.UserNotFoundException;
import com.wantfood.aplication.domain.model.User;
import com.wantfood.aplication.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceUserRegistration {
	
	private final UserRepository userRepository;

	private final ServiceGroupRegistration serviceGroupRegistration;

	@Transactional
	public User save(User user) {
		/*
		 * verificando se ja existe um user com um email cadastrado,
		 * fazendo uma verificação para apenas cadastrar usuários com e-mails diferentes
		 * condição que não permita cadastrar new usuário com um e-mail ja cadastrado, e 
		 * que possa atualizar um usuário sem ocorrer problema
		 * descarregando o jpa para n ter bug de gerenciamento 
		 * */
		userRepository.detach(user);
		Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
		
		if(existingUser.isPresent() && !existingUser.get().equals(user)) {
			throw new BusinessException(String.format("Já existe um usuário cadastrado com essse e-mail %s",
					user.getEmail()));
		}
		return userRepository.save(user);
	}
	
    @Transactional
    public void changePassword(Long userId, String currentPassword, String newPassword) {
        var user = fetchOrFail(userId);
        
        if (user.passwordNaoCoincideCom(currentPassword)) {
            throw new BusinessException("password atual informada não coincide com a password do usuário.");
        }
        
        user.setPassword(newPassword);
    }
    
    public User fetchOrFail(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
    } 
    
    @Transactional
    public void disassociateGroup(Long userId, Long groupId) {
    	var user = fetchOrFail(userId);
    	var group = serviceGroupRegistration.fetchOrFail(groupId);
    	
    	user.removeGroup(group);
    }
    
    @Transactional
    public void joinGroup(Long userId, Long groupId) {
    	var user = fetchOrFail(userId);
    	var group = serviceGroupRegistration.fetchOrFail(groupId);
    	
    	user.addGroup(group);
    }
}
