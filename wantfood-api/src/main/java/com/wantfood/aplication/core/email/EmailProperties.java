package com.wantfood.aplication.core.email;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("wantfood.email")
public class EmailProperties {
	
	//Colocando envio de e-mail como padrão FAKE
	private Implementacao impl = Implementacao.FAKE;
	
	private Sandbox sandbox = new Sandbox();
	
	@NotNull
	private String remetente;
	
	public enum Implementacao{
		SMTP, FAKE, SANDBOX
	}
	
	@Getter
	@Setter
	public class Sandbox{
		private String destinatario;
	}

}
