package com.wantfood.aplication.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

// implementando interface de validação, recebe o nome da anotação criada mais o tipo dela
public class FileSizeValidation implements ConstraintValidator <FileSize, MultipartFile>{
	
	//Representa o tamanho para bytes
	private DataSize maxSize;
	
	//inicializa o validador para preparar chamadas futuras do metodo isValid
	@Override
	public void initialize(FileSize constraintAnnotation) {
		//Passando uma string para saber a quantidade de bytes
		this.maxSize = DataSize.parse(constraintAnnotation.max());
	}
	
	//Metodo que possui a lógica
	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		return value == null || value.getSize() <= this.maxSize.toBytes();
	}
}
