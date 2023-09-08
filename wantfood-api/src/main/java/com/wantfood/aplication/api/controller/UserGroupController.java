package com.wantfood.aplication.api.controller;

import com.wantfood.aplication.api.assembler.GroupDTOAssembler;
import com.wantfood.aplication.api.model.GroupDTO;
import com.wantfood.aplication.domain.service.ServiceUserRegistration;
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
@RequestMapping("/users/{userId}/groups")
@RequiredArgsConstructor
public class UserGroupController {
	
    private final ServiceUserRegistration serviceUserRegistration;
    
    private final GroupDTOAssembler groupDTOAssembler;
    
    @GetMapping
    public List<GroupDTO> list(@PathVariable Long userId) {
        var user = serviceUserRegistration.fetchOrFail(userId);
        
        return groupDTOAssembler.toCollectionModel(user.getGroups());
    }
    
    @PutMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void connect(@PathVariable Long userId, @PathVariable Long groupId) {
        serviceUserRegistration.joinGroup(userId, groupId);
    }     
    
    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociate(@PathVariable Long userId, @PathVariable Long groupId) {
        serviceUserRegistration.disassociateGroup(userId, groupId);
    }
    
}
