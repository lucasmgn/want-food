package com.wantfood.aplication.api.controller;

import com.wantfood.aplication.api.assembler.StateDTOAssembler;
import com.wantfood.aplication.api.assembler.StateInputDisassembler;
import com.wantfood.aplication.api.model.StateDTO;
import com.wantfood.aplication.api.model.input.StateInputDTO;
import com.wantfood.aplication.domain.repository.StateRepository;
import com.wantfood.aplication.domain.service.StateServiceRegistration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

@RestController //Possui o @ResponsyBody e o @Controller
@RequestMapping("/states")
@RequiredArgsConstructor
public class StateController {
	
	private final StateRepository stateRepository;
	
	private final StateServiceRegistration registrationState;
	
	private final StateDTOAssembler stateDTOAssembler;
	
	private final StateInputDisassembler stateInputDisassembler;

	
	@GetMapping //Mapeando o metodo list para quando fizerem uma requisição get
	public List<StateDTO> list(){
		var todosstates = stateRepository.findAll();
		
		return stateDTOAssembler.toCollectionModel(todosstates);
	}
	
	@GetMapping("/{stateId}")
	public StateDTO find(@PathVariable Long stateId){
		
		var state = registrationState.fetchOrFail(stateId);
		
		return stateDTOAssembler.toModel(state);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public StateDTO add(@RequestBody @Valid StateInputDTO stateInputDTO) {
		var state = stateInputDisassembler.toDomainObject(stateInputDTO);
		
		state = registrationState.add(state);
		
		return stateDTOAssembler.toModel(state);
	}
	
	@PutMapping("/{stateId}")
	public StateDTO atualizar(@PathVariable Long stateId,
			@RequestBody @Valid StateInputDTO stateInputDTO){
		
		var stateAtual = registrationState.fetchOrFail(stateId);
		
		stateInputDisassembler.copyToDomainObject(stateInputDTO, stateAtual);
		
		stateAtual = registrationState.add(stateAtual);
		
		return stateDTOAssembler.toModel(stateAtual);
	}
	
	@DeleteMapping("/{stateId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long stateId){
		registrationState.delete(stateId);
	}

}
