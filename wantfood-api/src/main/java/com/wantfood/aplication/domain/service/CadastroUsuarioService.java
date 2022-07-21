package com.wantfood.aplication.domain.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wantfood.aplication.domain.exception.NegocioException;
import com.wantfood.aplication.domain.exception.UsuarioNaoEncontradoException;
import com.wantfood.aplication.domain.model.Grupo;
import com.wantfood.aplication.domain.model.Usuario;
import com.wantfood.aplication.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupo;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		/*
		 * verificando se ja existe um usuario com um email cadastrado,
		 * fazendo uma verificação para apenas cadastrar usuários com e-mails diferentes
		 * condição que não permita cadastrar novo usuário com um e-mail ja cadastrado, e 
		 * que possa atualizar um usuário sem ocorrer problema
		 * descarregando o jpa para n ter bug de gerenciamento 
		 * */
		usuarioRepository.detach(usuario);
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		
		if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(String.format("Já existe um usuário cadastrado com essse e-mail %s",
					usuario.getEmail()));
		}
		return usuarioRepository.save(usuario);
	}
	
    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = buscarOuFalhar(usuarioId);
        
        if (usuario.senhaNaoCoincideCom(senhaAtual)) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }
        
        usuario.setSenha(novaSenha);
    }
    
    public Usuario buscarOuFalhar(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
    } 
    
    @Transactional
    public void desassociarGrupo(Long usuarioId, Long grupoId) {
    	Usuario usuario = buscarOuFalhar(usuarioId);
    	Grupo grupo = cadastroGrupo.buscaOuFalha(grupoId);
    	
    	usuario.removerGrupo(grupo);
    }
    
    @Transactional
    public void associarGrupo(Long usuarioId, Long grupoId) {
    	Usuario usuario = buscarOuFalhar(usuarioId);
    	Grupo grupo = cadastroGrupo.buscaOuFalha(grupoId);
    	
    	usuario.adicionarGrupo(grupo);
    }
}
