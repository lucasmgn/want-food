package com.wantfood.aplication.core.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {FreteGratisValidator.class})
public @interface ValorZeroFreteGratuito {
	
	String message() default "descrição obrigatória está inválida";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
	String valorField();
	String descriptionField();
	String descriptionObrigatoria();
}