package com.wantfood.aplication.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.wantfood.aplication.core.storage.StorageProperties.TipoStorage;
import com.wantfood.aplication.domain.service.FotoStorageService;
import com.wantfood.aplication.infrastructure.service.storage.LocalFotoStorageService;
import com.wantfood.aplication.infrastructure.service.storage.S3FotoStorageService;

@Configuration
public class StorageConfig {
	
	@Autowired
	private StorageProperties storageProperties;
	
	//Criando uma instancia de Amazon S3
	@Bean
	@ConditionalOnProperty(name = "wantfood.storage.tipo", havingValue = "s3")
	AmazonS3 amazonS3() {	
		
		var credentials = new BasicAWSCredentials(
				storageProperties.getS3().getIdChaveAcesso(), 
				storageProperties.getS3().getChaveAcessoSecreta());
		
		return AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(storageProperties.getS3().getRegiao())
				.build();
	}
	
	@Bean
	FotoStorageService fotoStorageService() {	
		if(TipoStorage.S3.equals(storageProperties.getTipo())) {
			return new S3FotoStorageService();
		}else {
			return new LocalFotoStorageService();
		}
		
	}
}
