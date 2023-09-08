package com.wantfood.aplication.domain.service;

import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

public interface ShippingEmailService {
	
	void send(Message message);
	
	@Getter
	@Builder
	class Message {
		
		@Singular
		private Set<String> recipients;
		
		@NonNull
		private String subject;
		
		@NonNull
		private String body;
		
		@Singular("variavel")
		private Map<String, Object> variaveis;
	}
}
