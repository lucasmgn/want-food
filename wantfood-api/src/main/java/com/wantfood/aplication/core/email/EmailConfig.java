package com.wantfood.aplication.core.email;

import com.wantfood.aplication.domain.service.ShippingEmailService;
import com.wantfood.aplication.infrastructure.service.email.FakeShippingEmailService;
import com.wantfood.aplication.infrastructure.service.email.SandBoxEmailService;
import com.wantfood.aplication.infrastructure.service.email.SmtpShippingEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {
	
	 @Autowired
	 private EmailProperties emailProperties;
	 
	 @Bean
	 ShippingEmailService emailService() {
         return switch (emailProperties.getImpl()) {
             case FAKE -> new FakeShippingEmailService();
             case SMTP -> new SmtpShippingEmailService();
             case SANDBOX -> new SandBoxEmailService();
             default -> null;
         };
	}

}
