package com.wantfood.aplication.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wantfood.aplication.domain.service.EnvioEmailService;
import com.wantfood.aplication.infrastructure.service.email.FakeEnvioEmailService;
import com.wantfood.aplication.infrastructure.service.email.SandBoxEmailService;
import com.wantfood.aplication.infrastructure.service.email.SmtpEnvioEmailService;

@Configuration
public class EmailConfig {
	
	 @Autowired
	 private EmailProperties emailProperties;
	 
	 @Bean
	 EnvioEmailService emailService() {
		switch (emailProperties.getImpl()) {
		case FAKE:
			return new FakeEnvioEmailService();
		case SMTP:
			return new SmtpEnvioEmailService();
		case SANDBOX:
			return new SandBoxEmailService();
		default: 
			return null;
		}
	}

}
