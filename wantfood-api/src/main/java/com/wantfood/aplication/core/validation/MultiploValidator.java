package com.wantfood.aplication.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

// implementando interface de validação, recebe o name da anotação criada mais o tipo dela
public class MultiploValidator implements ConstraintValidator <Multiplo, Number>{

	private int numberMultiplo;
	
	//inicializa o validador para preparar chamadas futuras do metodo isValid
	@Override
	public void initialize(Multiplo constraintAnnotation) {
		this.numberMultiplo = constraintAnnotation.number();
	}
	
	//Metodo que possui a lógica
	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		boolean valido = true;
		
		if(value != null) {
			var valorDecimal = BigDecimal.valueOf(value.doubleValue());
			var multiploDecimal = BigDecimal.valueOf(this.numberMultiplo);
			//dividindo o valorDcimal com o multiplo decimal
			var resto = valorDecimal.remainder(multiploDecimal);
			
			//comparando 0 com o resto
			valido = BigDecimal.ZERO.compareTo(resto) == 0;
		}
				
		return valido;
	}

}
