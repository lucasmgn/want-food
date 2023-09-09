package com.wantfood.aplication.core.jackson;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

//Criado para ser um serializador do tipo Page

@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<?>>{

	//Serializando o tipo page do 0
	@Override
	public void serialize(Page<?> page, JsonGenerator gen, SerializerProvider serializers)
			throws IOException {
		
		gen.writeStartObject();
		
		//escrevendo uma propriedade de objeto, o name é content e o conteudo dela é o page.getContent
		gen.writeObjectField("content", page.getContent());
		
		//adicionando propriedades de paginação, podendo mudar os names das propriedades.
		gen.writeNumberField("size", page.getSize());
		gen.writeNumberField("totalElements", page.getTotalElements());
		gen.writeNumberField("totalPages", page.getTotalPages());
		gen.writeNumberField("number", page.getNumber());
		
		gen.writeEndObject();
	}

}
