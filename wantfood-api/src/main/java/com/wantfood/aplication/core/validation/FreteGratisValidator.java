package com.wantfood.aplication.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

public class FreteGratisValidator implements ConstraintValidator<ValorZeroFreteGratuito, Object>{
	
	private String valorField;
	private String descriptionField;
	private String descriptionObrigatoria;
	
	@Override
	public void initialize(ValorZeroFreteGratuito constraintAnnotation) {
		this.valorField = constraintAnnotation.valorField();
		this.descriptionField = constraintAnnotation.descriptionField();
		this.descriptionObrigatoria = constraintAnnotation.descriptionObrigatoria();
	}
	
	@Override
	public boolean isValid(Object objetoValidacao, ConstraintValidatorContext context) {
		boolean valido = true;
		
		//BeanUtils vai pegar o valor do frete para fazer a validação
		//Pegando o valor do metodo com o getReadMethod e invocando
		try {
			BigDecimal valor = (BigDecimal) BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), valorField)
					.getReadMethod().invoke(objetoValidacao);
			
			String description = (String) BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), descriptionField)
					.getReadMethod().invoke(objetoValidacao);
			
			//Fazendo a verificação
			if(valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && description != null) {
				//Verificando se o name tem "Frete Grátis", por ex: Points Burger - Frete Grátis
				valido = description.toLowerCase().contains(this.descriptionObrigatoria.toLowerCase());
			}
			
			return valido;
			
		} catch (Exception e) {
			throw new ValidationException(e.getCause());
		}			
	}

}
