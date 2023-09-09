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
public class PhotoProduct {
	
	@Id
	@EqualsAndHashCode.Include
	@Column(name = "product_id")
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId //Indica que a propriedade product é mapeada atraves do id da entidade PhotoProduct
	private Product product;
	
	private String nameFile;
	private String description;
	private String contentType;
	private Long size;
	
	 public Long getRestaurantId() {
		 if(getProduct() != null) {
			 return getProduct().getRestaurant().getId();
		 }
		return null;
	}
}
