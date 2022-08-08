package com.wantfood.aplication.infrastructure.service.storage;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.wantfood.aplication.domain.service.FotoStorageService;

@Service
public class LocalFotoStorageService implements FotoStorageService{
	
	@Value("${wantfood.storage.local.diretorio-fotos}")
	private Path diretorioFotos;
	
	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			//local de armazenamento da foto
			Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());
		
			//Copiando de um lugar e colocando em outro
			InputStream fotoOrigem = novaFoto.getInputStream();
			OutputStream destinoFoto = Files.newOutputStream(arquivoPath);
			
			FileCopyUtils.copy(fotoOrigem, destinoFoto);
			
		} catch (Exception e) {
			throw new StorageException("Não foi possível armazenar aquivo.", e);
		}
	}
	
	private Path getArquivoPath(String nomeArquivo) {
		return diretorioFotos.resolve(Path.of(nomeArquivo));
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
	public InputStream recuperar(String nomeArquivo) {
	    try {
	        Path arquivoPath = getArquivoPath(nomeArquivo);

	        return Files.newInputStream(arquivoPath);
	    } catch (Exception e) {
	        throw new StorageException("Não foi possível recuperar arquivo.", e);
	    }
	}

}
