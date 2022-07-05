package com.wantfood.aplication.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

public class FreteGratisValidator implements ConstraintValidator<ValorZeroFreteGratuito, Object>{
	
	private String valorField;
	private String descricaoField;
	private String descricaoObrigatoria;
	
	@Override
	public void initialize(ValorZeroFreteGratuito constraintAnnotation) {
		this.valorField = constraintAnnotation.valorField();
		this.descricaoField = constraintAnnotation.descricaoField();
		this.descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
	}
	
	@Override
	public boolean isValid(Object objetoValidacao, ConstraintValidatorContext context) {
		boolean valido = true;
		
		//BeanUtils vai pegar o valor do frete para fazer a validação
		//Pegando o valor do metodo com o getReadMethod e invocando
		try {
			BigDecimal valor = (BigDecimal) BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), valorField)
					.getReadMethod().invoke(objetoValidacao);
			
			String descricao = (String) BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), descricaoField)
					.getReadMethod().invoke(objetoValidacao);
			
			//Fazendo a verificação
			if(valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null) {
				//Verificando se o nome tem "Frete Grátis", por ex: Points Burger - Frete Grátis
				valido = descricao.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());
			}
			
			return valido;
			
		} catch (Exception e) {
			throw new ValidationException(e.getCause());
		}			
	}

}
