package com.wantfood.aplication.infrastructure.service.email;

import com.wantfood.aplication.core.email.EmailProperties;
import com.wantfood.aplication.domain.service.ShippingEmailService;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class SmtpShippingEmailService implements ShippingEmailService{

	//Instancia para enviar e-mail
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private EmailProperties emailProperties;

	//Instanciando configuração do freemarker
	@Autowired
	private Configuration freemarkerConfig;

	@Override
	public void send(Message Message) {
		try {
			var mimeMessage = createMimeMessage(Message);

			mailSender.send(mimeMessage);
		} catch (Exception e) {
			throw new EmailException("Não foi possível enviar e-mail", e);
		}
	}

	protected MimeMessage createMimeMessage(Message message) throws MessagingException {
		var body = processarTemplate(message);

		var mimeMessage = mailSender.createMimeMessage();

		var helper = new MimeMessageHelper(mimeMessage, "UTF-8");
		helper.setFrom(emailProperties.getRemetente());
		helper.setTo(message.getRecipients().toArray(new String[0]));
		helper.setSubject(message.getSubject());
		helper.setText(body, true); //true indicando qeu o texto é em html

		return mimeMessage;
	}

	protected String processarTemplate(Message message) {
		try {
			var template = freemarkerConfig.getTemplate(message.getBody());

			return FreeMarkerTemplateUtils.processTemplateIntoString(template, message.getVariaveis());
		} catch (Exception e) {
			throw new EmailException("Não foi possivel montar o template do e-mail", e);
		}
	}
}
