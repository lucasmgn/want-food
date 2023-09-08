package com.wantfood.aplication.infrastructure.service.storage;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.wantfood.aplication.core.storage.StorageProperties;
import com.wantfood.aplication.domain.service.PhotoStorageService;
	
public class LocalPhotoStorageService implements PhotoStorageService {
	
	@Autowired
	private StorageProperties storageProperties;
	
	@Override
	public void store(NewPicture novaFoto) {
		try {
			//local de armazenamento da photo
			var filePath = getFilePath(novaFoto.getNameFile());
		
			//Copiando de um lugar e colocando em outro
			FileCopyUtils.copy(novaFoto.getInputStream(), 
					Files.newOutputStream(filePath));
			
		} catch (Exception e) {
			throw new StorageException("Não foi possível store aquivo.", e);
		}
	}
	
	private Path getFilePath(String nameFile) {
		return storageProperties.getLocal()
				.getDiretorioFotos()
				.resolve(Path.of(nameFile));
	}

	@Override
	public void remove(String nameFile) {
		try {
			//Pegando file
			Path filePath = getFilePath(nameFile);
			
			//Deletando file
			Files.deleteIfExists(filePath);
		} catch (Exception e) {
			throw new StorageException("Não foi possível delete aquivo.", e);
		}
	}

	//Recuperando um file
	@Override
	public PhotoRecovered recover(String nameFile) {
	    try {
	        Path filePath = getFilePath(nameFile);
	        
	        return PhotoRecovered.builder()
	        		.inputStream(Files.newInputStream(filePath))
	        		.build();
	    } catch (Exception e) {
	        throw new StorageException("Não foi possível recover file.", e);
	    }
	}

}
