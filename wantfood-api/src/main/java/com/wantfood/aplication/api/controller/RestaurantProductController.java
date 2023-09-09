package com.wantfood.aplication.api.controller;

import com.wantfood.aplication.api.assembler.ProductDTOAssembler;
import com.wantfood.aplication.api.assembler.ProductInputDisassembler;
import com.wantfood.aplication.api.model.ProductDTO;
import com.wantfood.aplication.api.model.input.ProductInputDTO;
import com.wantfood.aplication.domain.model.Product;
import com.wantfood.aplication.domain.repository.ProductRepository;
import com.wantfood.aplication.domain.service.ProductRegistrationService;
import com.wantfood.aplication.domain.service.RegistrationRestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products")
@RequiredArgsConstructor
public class RestaurantProductController {

    private final ProductRepository productRepository;
    
    private final ProductRegistrationService productRegistrationService;
    
    private final RegistrationRestaurantService registrationRestaurantService;
    
    private final ProductDTOAssembler productModelAssembler;
    
    private final ProductInputDisassembler productInputDisassembler;
	
    /*
     * @RequestParam (required = false) boolean incluiinactives
     * Fazendo a lsitagem de todos os products ou trazendo apenas os que tem a 
     * propriedade ativa
     * (key = includeInActives value = true), imprime todos os products até os inactives,
     * caso não seja selecionada ou marcada como false, mostrará apenas os products que estão
     * com a propriedade active = true
     * 
     * */
    @GetMapping
    public List<ProductDTO> list(@PathVariable Long restaurantId,
                                   @RequestParam (required = false) boolean includeInActives){
    	
        var restaurant = registrationRestaurantService.fetchOrFail(restaurantId);
        
        List<Product> todosProducts = null;
        
        if(includeInActives) {
        	todosProducts = productRepository.findByRestaurant(restaurant);
        }else {
        	todosProducts = productRepository.findActiveByRestaurant(restaurant);
        }
        
        return productModelAssembler.toCollectionModel(todosProducts);
    }

    @GetMapping("/{productId}")
    public ProductDTO find(@PathVariable Long restaurantId, @PathVariable Long productId) {
    	var product = productRegistrationService.fetchOrFail(restaurantId, productId);
    			
        return productModelAssembler.toModel(product);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO add(@PathVariable Long restaurantId,
            @RequestBody @Valid ProductInputDTO productInput) {

        var restaurant = registrationRestaurantService.fetchOrFail(restaurantId);
        
        var product = productInputDisassembler.toDomainObject(productInput);
        product.setRestaurant(restaurant);
        
        product = productRegistrationService.save(product);
        
        return productModelAssembler.toModel(product);
    }
	
    @PutMapping("/{productId}")
    public ProductDTO atualizar(@PathVariable Long restaurantId, @PathVariable Long productId,
            @RequestBody @Valid ProductInputDTO productInput) {

        var productAtual = productRegistrationService.fetchOrFail(restaurantId, productId);
        
        productInputDisassembler.copyToDomainObject(productInput, productAtual);
        productAtual = productRegistrationService.save(productAtual);
        
        return productModelAssembler.toModel(productAtual);
    }   
}