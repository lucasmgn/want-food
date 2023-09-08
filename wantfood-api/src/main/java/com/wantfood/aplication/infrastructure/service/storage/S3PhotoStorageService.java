package com.wantfood.aplication.infrastructure.service.storage;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.wantfood.aplication.core.storage.StorageProperties;
import com.wantfood.aplication.domain.service.PhotoStorageService;

public class S3PhotoStorageService implements PhotoStorageService {

	@Autowired
	private AmazonS3 amazonS3;
	
	@Autowired
	private StorageProperties storageProperties;
	
	@Override
	public void store(NewPicture novaFoto) {
		
		try {
			String caminhoFile = getCaminhoFile(novaFoto.getNameFile());
			
			var objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(novaFoto.getContentType());
			
			var putObjectRequest = new PutObjectRequest(
					getBucket(),
					caminhoFile,
					novaFoto.getInputStream(),
					objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead);
			
			amazonS3.putObject(putObjectRequest);
		} catch (Exception e) {
			throw new StorageException("Não foi possível enviar file para Amazon S3.", e);
		}
	}

	@Override
	public void remove(String nameFile) {
		
		try {
			String caminhoFile = getCaminhoFile(nameFile);
			
			var deleteObjectRequest = new DeleteObjectRequest(
					getBucket(), caminhoFile);
			
			amazonS3.deleteObject(deleteObjectRequest);
		} catch (Exception e) {
			throw new StorageException("Não foi possível delete file para Amazon S3.", e);
		}
	}

	@Override
	public PhotoRecovered recover(String nameFile) {
		String caminhoFile = getCaminhoFile(nameFile);
		
		URL url = amazonS3.getUrl(getBucket(), caminhoFile);
		
		return PhotoRecovered.builder().url(url.toString()).build();
	}


	private String getCaminhoFile(String nameFile) {
		return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nameFile);
	}
	
	private String getBucket() {
		return storageProperties.getS3().getBucket();
	}
}
