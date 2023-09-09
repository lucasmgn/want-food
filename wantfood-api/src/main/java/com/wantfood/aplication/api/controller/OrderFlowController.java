package com.wantfood.aplication.api.controller;

import com.wantfood.aplication.domain.service.OrderFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders/{codeOrder}")
@RequiredArgsConstructor
public class OrderFlowController {
	
	private final OrderFlowService orderFlowService;
	
	@PutMapping("/confirmation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirm(@PathVariable String codeOrder) {
		 orderFlowService.confirm(codeOrder);
	}
	
	@PutMapping("/delivery")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delivered(@PathVariable String codeOrder) {
		 orderFlowService.deliver(codeOrder);
	}
	
	@PutMapping("/cancelamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancel(@PathVariable String codeOrder) {
		 orderFlowService.cancel(codeOrder);
	}
}
