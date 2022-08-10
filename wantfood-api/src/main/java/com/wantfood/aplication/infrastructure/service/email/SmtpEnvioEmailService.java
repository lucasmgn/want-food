package com.wantfood.aplication.infrastructure.service.email;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.wantfood.aplication.core.email.EmailProperties;
import com.wantfood.aplication.domain.service.EnvioEmailService;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService{

	//Instancia para enviar e-mail
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private EmailProperties emailProperties;
	
	@Override
	public void enviar(Mensagem mensagem) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
		
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
			helper.setFrom(emailProperties.getRemetente());
			helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
			helper.setSubject(mensagem.getAssunto());
			helper.setText(mensagem.getCorpo(), true); //true indicando qeu o texto é em html
			
			mailSender.send(mimeMessage);
			
		} catch (Exception e) {
			throw new EmailException("Não foi possivel enviar e-mail", e);
		}
	}
	
}
