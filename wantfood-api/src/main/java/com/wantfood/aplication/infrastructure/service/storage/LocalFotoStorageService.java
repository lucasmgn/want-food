package com.wantfood.aplication.infrastructure.service.storage;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.wantfood.aplication.core.storage.StorageProperties;
import com.wantfood.aplication.domain.service.FotoStorageService;

//@Service	
public class LocalFotoStorageService implements FotoStorageService{
	
	@Autowired
	private StorageProperties storageProperties;
	
	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			//local de armazenamento da foto
			Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());
		
			//Copiando de um lugar e colocando em outro
			FileCopyUtils.copy(novaFoto.getInputStream(), 
					Files.newOutputStream(arquivoPath));
			
		} catch (Exception e) {
			throw new StorageException("Não foi possível armazenar aquivo.", e);
		}
	}
	
	private Path getArquivoPath(String nomeArquivo) {
		return storageProperties.getLocal()
				.getDiretorioFotos()
				.resolve(Path.of(nomeArquivo));
	}

	@Override
	public void remover(String nomeArquivo) {
		try {
			//Pegando arquivo
			Path arquivoPath = getArquivoPath(nomeArquivo);
			
			//Deletando arquivo
			Files.deleteIfExists(arquivoPath);
		} catch (Exception e) {
			throw new StorageException("Não foi possível excluir aquivo.", e);
		}
	}

	//Recuperando um arquivo
	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {
	    try {
	        Path arquivoPath = getArquivoPath(nomeArquivo);
	        
	        FotoRecuperada fotoRecuperada = FotoRecuperada.builder()
	        		.inputStream(Files.newInputStream(arquivoPath))
	        		.build();
	        
	        return fotoRecuperada; 
	    } catch (Exception e) {
	        throw new StorageException("Não foi possível recuperar arquivo.", e);
	    }
	}

}
