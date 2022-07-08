package com.wantfood.aplication.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.wantfood.aplication.core.validation.ValorZeroFreteGratuito;

import lombok.Data;
import lombok.EqualsAndHashCode;

//Criando constraints de validação customizadas em nível de classe
@ValorZeroFreteGratuito(valorField = "taxaFrete", descricaoField = "nome",
descricaoObrigatoria = "Frete Grátis")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/*
	 * @NotNull, quando for criado um novo restaurante ele não pode ser nulo
	 * @NotEmpty, não aceita valores vázios e nem null
	 * @NotBlank, vai validar que não pode ser vázio, null ou ter apenas espaços em branco
	 * @NotBlank(groups = Groups.CadastroRestaurantes.class),
	 *  podendo colocar mais de um grupo {Groups.CadastroRestaurantes.class}
	 *  Menssagem do notBlank está em message.properties
	 * */
//	@NotBlank
	@Column(nullable = false)
	private String nome;
	
	/*
	 * @DecimalMin("0"), No mínimo a taxaFrete precisa ter um valor de 0
	 * @PositiveOrZero(groups = Groups.CadastroRestaurante.class)
	 * @TaxaFrete //constraint de composição
	 * @Multiplo(numero = 5) //Constraint customizade com implementacao do ConstraintValidator
	 * */	
//	@NotNull 
//	@PositiveOrZero
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;

	/*
	 * @JsonIgnore
	 * @Valid, validando operações em cascata, as propriedades de cozinha seram verificadas
	 * @ManyToOne(fetch = FetchType.LAZY), muitos restaurantes tem uma cozinha, todo ToOne é eager,
	 * sempre será carregado com a entidade
	 * @NotNull(groups = Groups.CozinhaId.class), validando apenas para o group
	 * @ConvertGroup(from = Default.class, to = Groups.CozinhaId.class),
	 * na hora que for validar cozinha irá transforar de default para CadastroRestaurante,
	 * diminuindo a complexidade do código 
	 * @JsonIgnoreProperties("nome") ignorando a propriedade nome de cozinha na classe restaurante
	 * allowGetters = true aceitando metodos de getter na aplicação
	 * */
//	@Valid
//	@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
//	@NotNull
	@ManyToOne
	@JoinColumn(name = "cozinha_id", nullable= false)
	private Cozinha cozinha;

	@Embedded //Tipo incorporado
	private Endereco endereco;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;
	 
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime") //para tirar a precisão de milissegundos
	private OffsetDateTime dataAtualizacao;
	
	@ManyToMany //Tudo que termina com ToMany tem a estrátegia de carregamento lazy
	@JoinTable(name = "restaurante_forma_pagamento",
			joinColumns = @JoinColumn(name = "restaurante_id"),
			inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private List<FormaPagamento> formasPagamento = new ArrayList<>();
	
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();


}
