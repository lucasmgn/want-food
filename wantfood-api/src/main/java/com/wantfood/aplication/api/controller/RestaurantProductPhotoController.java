package com.wantfood.aplication.api.controller;

import com.wantfood.aplication.api.assembler.PhotoProductDTOAssembler;
import com.wantfood.aplication.api.model.PhotoProductDTO;
import com.wantfood.aplication.api.model.input.PhotoProductInput;
import com.wantfood.aplication.domain.exception.EntityNotFoundException;
import com.wantfood.aplication.domain.model.PhotoProduct;
import com.wantfood.aplication.domain.service.CatalogPhotoProductService;
import com.wantfood.aplication.domain.service.PhotoStorageService;
import com.wantfood.aplication.domain.service.ProductRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
@RequiredArgsConstructor
public class RestaurantProductPhotoController {
	
	private final CatalogPhotoProductService photoProductService;
	
	private final ProductRegistrationService productService;
	
	private final PhotoProductDTOAssembler assembler;
	
	private final PhotoStorageService photoStorageService;
	
	//Primeiro metodo get será retornado em json
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public PhotoProductDTO find(@PathVariable Long restaurantId,
			@PathVariable Long productId){
			
		var photoProduct = photoProductService.fetchOrFail(restaurantId, productId);
		
		return assembler.toModel(photoProduct);
		
	}
	
	//Retornará uma imagem
	@GetMapping
	public ResponseEntity<?> servePhoto(@PathVariable Long restaurantId,
			@PathVariable Long productId, @RequestHeader(name = "accept") String acceptHeader){

		try {
			var photoProduct = photoProductService.fetchOrFail(restaurantId, productId);
			
			//Verificando compatibilidade, parseMediaType converte uma string em media type
			var mediaType = MediaType.parseMediaType(photoProduct.getContentType());
			var mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);
			
			checkMediaType(mediaType, mediaTypesAceitas);
			
			var photoRecuperada = photoStorageService.recover(photoProduct.getNameFile());
			
			if (photoRecuperada.temUrl()) {
				//Retornando a url
				return ResponseEntity.status(HttpStatus.FOUND)
						.header(HttpHeaders.LOCATION, photoRecuperada.getUrl())
						.build();
			}else {
				return ResponseEntity.ok()
						.contentType(mediaType)
						.body(new InputStreamResource(photoRecuperada.getInputStream()));
			}
		}catch(EntityNotFoundException | HttpMediaTypeNotAcceptableException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public PhotoProductDTO updatePhoto(@PathVariable Long restaurantId,
			@PathVariable Long productId, @Valid PhotoProductInput photoProductInput) throws IOException {
		
		var product = productService.fetchOrFail(restaurantId, productId);
		
		var file = photoProductInput.getFile();
		
		var photoProduct = new PhotoProduct();
		photoProduct.setProduct(product);
		photoProduct.setDescription(photoProductInput.getDescription());
		photoProduct.setContentType(file.getContentType());
		photoProduct.setSize(file.getSize());
		photoProduct.setNameFile(file.getOriginalFilename());
		
		var photo = photoProductService.save(photoProduct, file.getInputStream());
		return assembler.toModel(photo);
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removePhoto(@PathVariable Long restaurantId, @PathVariable Long productId) {
		
		photoProductService.remove(restaurantId, productId);
	}
	
	//Metodo de verificação
	private void checkMediaType(MediaType media, List<MediaType> acceptedList)
			throws HttpMediaTypeNotAcceptableException {
		
		//Se pelo menos um for compativel retornar true
		var isCompatible = acceptedList.stream()
				.anyMatch(mediaAccepted -> mediaAccepted.isCompatibleWith(media));
		
		if(!isCompatible) {
			throw new HttpMediaTypeNotAcceptableException(acceptedList);
		}
	}
	
}
