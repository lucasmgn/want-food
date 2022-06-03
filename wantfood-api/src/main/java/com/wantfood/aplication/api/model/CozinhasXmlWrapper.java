package com.wantfood.aplication.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.wantfood.aplication.domain.model.Cozinha;

import lombok.Data;
import lombok.NonNull;

@JacksonXmlRootElement(localName = "cozinhas")
@Data
public class CozinhasXmlWrapper {
 
	@JsonProperty(value = "cozinha")
	@JacksonXmlElementWrapper(useWrapping = false) //desabilitando o embrulho do wrapper, evitando a repetição na estrutura xml
	@NonNull //gerando construtores
	private List<Cozinha> cozinhas;
}
