package com.wantfood.aplication.api.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.wantfood.aplication.api.assembler.UsuarioDTOAssembler;
import com.wantfood.aplication.api.assembler.UsuarioInputDisassembler;
import com.wantfood.aplication.api.model.UsuarioDTO;
import com.wantfood.aplication.api.model.input.SenhaInputDTO;
import com.wantfood.aplication.api.model.input.UsuarioComSenhaInputDTO;
import com.wantfood.aplication.api.model.input.UsuarioInputDTO;
import com.wantfood.aplication.domain.model.Usuario;
import com.wantfood.aplication.domain.repository.UsuarioRepository;
import com.wantfood.aplication.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {
	
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private CadastroUsuarioService cadastroUsuario;
    
    @Autowired
    private UsuarioDTOAssembler usuarioDTOAssembler;
    
    @Autowired
    private UsuarioInputDisassembler usuarioInputDisassembler;
    
    @GetMapping
    public List<UsuarioDTO> listar(){
    	List<Usuario> todosUsuarios = usuarioRepository.findAll();
    	
    	return usuarioDTOAssembler.toCollectionModel(todosUsuarios);
    }
    
    @GetMapping("/{usuarioId}")
    public UsuarioDTO buscar(@PathVariable Long usuarioId) {
    	Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
    	return usuarioDTOAssembler.toModel(usuario);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO adicionar(@RequestBody @Valid UsuarioComSenhaInputDTO usuarioComSenhaInputDTO) {
    	Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioComSenhaInputDTO);
    	usuario = cadastroUsuario.salvar(usuario);
    	
    	return usuarioDTOAssembler.toModel(usuario);
    }
    
    @PutMapping("/{usuarioId}")
    public UsuarioDTO atualizar(@PathVariable Long usuarioId,
    		@RequestBody @Valid UsuarioInputDTO usuarioInputDTO) {
    	Usuario usuarioAtual = cadastroUsuario.buscarOuFalhar(usuarioId);
    	usuarioInputDisassembler.copyToDomainObject(usuarioInputDTO, usuarioAtual);
    	usuarioAtual = cadastroUsuario.salvar(usuarioAtual);
    	
    	return usuarioDTOAssembler.toModel(usuarioAtual);
    }
    
    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInputDTO senha) {
        cadastroUsuario.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
    }           
}
