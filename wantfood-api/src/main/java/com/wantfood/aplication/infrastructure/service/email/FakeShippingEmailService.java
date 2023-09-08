package com.wantfood.aplication.infrastructure.service.email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeShippingEmailService extends SmtpShippingEmailService{
	
	public void enviar(Message Message) {
		String body = processarTemplate(Message);
		
		log.info("[FAKE E-MAIL] Para: {}\n{}", Message.getRecipients(), body);
	}
}
