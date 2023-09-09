package com.wantfood.aplication.infrastructure.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.wantfood.aplication.core.email.EmailProperties;

public class SandBoxEmailService extends SmtpShippingEmailService {

	  @Autowired
	  private EmailProperties emailProperties;
	  
		// Separei a criação de MimeMessage em um método na classe pai (createMimeMessage),
		// para possibilitar a sobrescrita desse método aqui
	  @Override
	  protected MimeMessage createMimeMessage(Message message) throws MessagingException {
			var mimeMessage = super.createMimeMessage(message);
		
			var helper = new MimeMessageHelper(mimeMessage, "UTF-8");
			helper.setTo(emailProperties.getSandbox().getDestinatario());
			
			return mimeMessage;
	}
	
}
