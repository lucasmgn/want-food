package com.wantfood.aplication.api.controller;

import com.wantfood.aplication.api.assembler.UserDTOAssembler;
import com.wantfood.aplication.api.assembler.UserInputDisassembler;
import com.wantfood.aplication.api.model.UserDTO;
import com.wantfood.aplication.api.model.input.PasswordInputDTO;
import com.wantfood.aplication.api.model.input.UserInputDTO;
import com.wantfood.aplication.api.model.input.UserWithPasswordInputDTO;
import com.wantfood.aplication.domain.repository.UserRepository;
import com.wantfood.aplication.domain.service.ServiceUserRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {
	
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ServiceUserRegistration serviceUserRegistration;
    
    @Autowired
    private UserDTOAssembler userDTOAssembler;
    
    @Autowired
    private UserInputDisassembler userInputDisassembler;
    
    @GetMapping
    public List<UserDTO> list(){
    	var todosUsers = userRepository.findAll();
    	
    	return userDTOAssembler.toCollectionModel(todosUsers);
    }
    
    @GetMapping("/{userId}")
    public UserDTO find(@PathVariable Long userId) {
    	var user = serviceUserRegistration.fetchOrFail(userId);
    	return userDTOAssembler.toModel(user);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO add(@RequestBody @Valid UserWithPasswordInputDTO userComPasswordInputDTO) {
    	var user = userInputDisassembler.toDomainObject(userComPasswordInputDTO);
    	user = serviceUserRegistration.save(user);
    	
    	return userDTOAssembler.toModel(user);
    }
    
    @PutMapping("/{userId}")
    public UserDTO atualizar(@PathVariable Long userId,
    		@RequestBody @Valid UserInputDTO userInputDTO) {
    	var userCurrent = serviceUserRegistration.fetchOrFail(userId);
    	userInputDisassembler.copyToDomainObject(userInputDTO, userCurrent);
    	userCurrent = serviceUserRegistration.save(userCurrent);
    	
    	return userDTOAssembler.toModel(userCurrent);
    }
    
    @PutMapping("/{userId}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable Long userId, @RequestBody @Valid PasswordInputDTO password) {
        serviceUserRegistration.changePassword(userId, password.getCurrentPassword(), password.getNewPassword());
    }           
}
