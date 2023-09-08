package com.wantfood.aplication.api.controller;

import com.wantfood.aplication.api.assembler.GroupDTOAssembler;
import com.wantfood.aplication.api.assembler.GroupInputDisassembler;
import com.wantfood.aplication.api.model.GroupDTO;
import com.wantfood.aplication.api.model.input.GroupInputDTO;
import com.wantfood.aplication.api.openapi.controller.GroupControllerOpenApi;
import com.wantfood.aplication.domain.repository.GroupRepository;
import com.wantfood.aplication.domain.service.ServiceGroupRegistration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping(value = "/groups", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GroupController implements GroupControllerOpenApi{
	
	private final ServiceGroupRegistration serviceGroupRegistration;
	
	private final GroupRepository groupRepository;
	
	private final GroupDTOAssembler groupDTOAssembler;
	
	private final GroupInputDisassembler groupInputDisassembler;
	
	@GetMapping
	public List<GroupDTO> list(){
		var todosGroups = groupRepository.findAll();
		
		return groupDTOAssembler.toCollectionModel(todosGroups);
	}
	
	@GetMapping("/{groupId}")
	public GroupDTO find(@PathVariable @Valid Long groupId) {
		var group = serviceGroupRegistration.fetchOrFail(groupId);
		
		return groupDTOAssembler.toModel(group);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GroupDTO add(@RequestBody @Valid GroupInputDTO groupInputDTO) {
		
		var group = groupInputDisassembler.toDomainObject(groupInputDTO);
		group = serviceGroupRegistration.add(group);
		
		return groupDTOAssembler.toModel(group);
	}
	
	@PutMapping("/{groupId}")
	public GroupDTO atualizar(@PathVariable Long groupId,
			@RequestBody @Valid GroupInputDTO groupInputDTO) {

		var groupCurrent = serviceGroupRegistration.fetchOrFail(groupId);

		groupInputDisassembler.copyToDomainObject(groupInputDTO, groupCurrent);

		groupCurrent = serviceGroupRegistration.add(groupCurrent);
		
		return groupDTOAssembler.toModel(groupCurrent);
	}
	
	@DeleteMapping("/{groupId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long groupId) {
		serviceGroupRegistration.delete(groupId);
	}
	
}
