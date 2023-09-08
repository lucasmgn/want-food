package com.wantfood.aplication.domain.service;

import com.wantfood.aplication.domain.exception.PhotoProductNotFoundException;
import com.wantfood.aplication.domain.model.PhotoProduct;
import com.wantfood.aplication.domain.repository.ProductRepository;
import com.wantfood.aplication.domain.service.PhotoStorageService.NewPicture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class CatalogPhotoProductService {
	
	//Utilizando o repositorio de product pq PhotoProduct está dentro do agregado de Produto
	private final ProductRepository productRepository;
	
	private final PhotoStorageService storageService;

	public PhotoProduct fetchOrFail(Long restaurantId, Long productId) {
		return productRepository.findPhotoProductById(restaurantId, productId)
				.orElseThrow(() -> new PhotoProductNotFoundException(restaurantId, productId));
		
	}
	
	@Transactional
	public PhotoProduct save(PhotoProduct photo, InputStream inputStream) {
		/*
		 * Caso tente save novamente a photo,
		 * a antiga será e a nova será adicionada, delete se existir
		 * */
		var restaurantId = photo.getRestaurantId();
		var productId = photo.getProduct().getId();
		var nameNovoFile = storageService.generateNameFile(photo.getNameFile());
		String nameFileExisting = null;
		
		var photoExisting = productRepository
				.findPhotoProductById(restaurantId,productId);
		
		if(photoExisting.isPresent()) {
			//delete photo do bd e da maquina local
			nameFileExisting = photoExisting.get().getNameFile();
			productRepository.delete(photoExisting.get());
		}
		
		//Salvando a photo no bd antes de store, para que se der algum problema
		//ele venha antes do armazenamento, fazendo um flush 
		photo.setNameFile(nameNovoFile);
		photo = productRepository.save(photo);
		productRepository.flush();
		
		var newPhoto = NewPicture.builder()
				.nameFile(photo.getNameFile())
				.contentType(photo.getContentType())
				.inputStream(inputStream)
				.build();
		
		storageService.replace(nameFileExisting ,newPhoto);
				
		return photo;
	}
	
	@Transactional
	public void remove(Long restaurantId, Long productId) {
		
			var photoRemove = fetchOrFail(restaurantId, productId);
			
			productRepository.delete(photoRemove);
			productRepository.flush();
			
			storageService.remove(photoRemove.getNameFile());
	}
}
