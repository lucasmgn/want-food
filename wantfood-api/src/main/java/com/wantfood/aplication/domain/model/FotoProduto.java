package com.wantfood.aplication.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class FotoProduto {
	
	@Id
	@EqualsAndHashCode.Include
	@Column(name = "produto_id")
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId //Indica que a propriedade produto é mapeada atraves do id da entidade FotoProduto
	private Produto produto;
	
	private String nomeArquivo;
	private String descricao;
	private String contentType;
	private Long tamanho;
	
	 public Long getRestauranteId() {
		 if(getProduto() != null) {
			 return getProduto().getRestaurante().getId();
		 }
		return null;
	}
}
