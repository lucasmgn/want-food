package com.wantfood.aplication.api.controller;

import com.wantfood.aplication.api.assembler.OrderDTOAssembler;
import com.wantfood.aplication.api.assembler.OrderInputDisassembler;
import com.wantfood.aplication.api.assembler.OrderResumeDTOAssembler;
import com.wantfood.aplication.api.model.OrderDTO;
import com.wantfood.aplication.api.model.OrderSummaryDTO;
import com.wantfood.aplication.api.model.input.OrderInputDTO;
import com.wantfood.aplication.core.data.PageableTranslator;
import com.wantfood.aplication.domain.exception.BusinessException;
import com.wantfood.aplication.domain.exception.EntityNotFoundException;
import com.wantfood.aplication.domain.filter.OrderFilter;
import com.wantfood.aplication.domain.model.User;
import com.wantfood.aplication.domain.repository.OrderRepository;
import com.wantfood.aplication.domain.service.ServiceOrderRegistration;
import com.wantfood.aplication.infrastructure.repository.spec.OrderSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderRepository orderRepository;
	
	private final ServiceOrderRegistration serviceOrderRegistration;
	
	private final OrderDTOAssembler orderDTOAssembler;
	
	//Resume dos atributos orders, para ficar uma requisição mais limpa
	private final OrderResumeDTOAssembler orderResumeDTOAssembler;
	
	private final OrderInputDisassembler orderInputDisassembler;

	@GetMapping
	public Page<OrderSummaryDTO> research(@PageableDefault(size = 10) Pageable pageable
			, OrderFilter filter){
		pageable = traduzPageable(pageable);
		
		var ordersPage = orderRepository.findAll
				(OrderSpecs.usingFilter(filter), pageable);
		
		var orderDTO =  orderResumeDTOAssembler.
				toCollectionModel(ordersPage.getContent());

		return new PageImpl<>(orderDTO , pageable,
				ordersPage.getTotalElements());
	}
	
	@GetMapping("/{codeOrder}")
	public OrderDTO find(@PathVariable String codeOrder) {
		var order = serviceOrderRegistration.fetchOrFail(codeOrder);
	 
		return orderDTOAssembler.toModel(order);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrderDTO add(@RequestBody @Valid OrderInputDTO orderInputDTO) {
		
		try {
			var newOrder = orderInputDisassembler.toDomainObject(orderInputDTO);
			
			newOrder.setClient(new User());
			newOrder.getClient().setId(1L);
			
			newOrder = serviceOrderRegistration.emitir(newOrder);
			
			return orderDTOAssembler.toModel(newOrder);
		}catch(EntityNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	//traduzindo os campos de valores no Postman para n dar exception
	//ex: value = clientname, o metodo irá trabuzir ele para : client.name
	private Pageable traduzPageable(Pageable pageable) {
		
		var mapeamento = Map.of(
				"code", "code",
				"subTotal", "subTotal",
				"rateShipping", "rateShipping",
				"restaurant.name", "restaurant.name",
				"nameclient", "client.name",
				"amount", "amount"	
			);
		
		return PageableTranslator.translate(pageable, mapeamento);
	}
}
