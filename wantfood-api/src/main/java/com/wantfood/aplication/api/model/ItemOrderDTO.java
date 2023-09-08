package com.wantfood.aplication.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemOrderDTO {
	
    private Long productId;
    private String productName;
    private Integer amount;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private String observation;
}
