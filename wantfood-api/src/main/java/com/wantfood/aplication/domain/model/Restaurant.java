package com.wantfood.aplication.domain.model;

import com.wantfood.aplication.core.validation.ValorZeroFreteGratuito;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//Criando constraints de validação customizadas em nível de classe
@ValorZeroFreteGratuito(valorField = "rateShipping", descriptionField = "name",
descriptionObrigatoria = "Frete Grátis")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurant {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/*
	 * @NotNull, quando for criado um new restaurant ele não pode ser nulo
	 * @NotEmpty, não aceita valores vázios e nem null
	 * @NotBlank, vai validar que não pode ser vázio, null ou ter apenas espaços em branco
	 * @NotBlank(groups = Groups.Cadastrorestaurants.class),
	 *  podendo colocar mais de um group {Groups.Cadastrorestaurants.class}
	 *  Menssagem do notBlank está em message.properties
	 * */

	@Column(nullable = false)
	private String name;
	
	/*
	 * @DecimalMin("0"), No mínimo a rateShipping precisa ter um valor de 0
	 * @PositiveOrZero(groups = Groups.Cadastrorestaurant.class)
	 * @rateShipping //constraint de composição
	 * @Multiplo(number = 5) //Constraint customizade com implementacao do ConstraintValidator
	 * */	

	@Column(name = "rate_shipping", nullable = false)
	private BigDecimal rateShipping;

	/*
	 * @JsonIgnore
	 * @Valid, validando operações em cascata, as propriedades de kitchen seram verificadas
	 * @ManyToOne(fetch = FetchType.LAZY), muitos restaurants tem uma kitchen, todo ToOne é eager,
	 * sempre será carregado com a entidade
	 * @NotNull(groups = Groups.KitchenId.class), validando apenas para o group
	 * @ConvertGroup(from = Default.class, to = Groups.KitchenId.class),
	 * na hora que for validar kitchen irá transforar de default para Cadastrorestaurant,
	 * diminuindo a complexidade do código 
	 * @JsonIgnoreProperties("name") ignorando a propriedade name de kitchen na classe restaurant
	 * allowGetters = true aceitando metodos de getter na aplicação
	 * */

	@ManyToOne
	@JoinColumn(name = "kitchen_id", nullable= false)
	private Kitchen kitchen;

	@Embedded //Tipo inbodyrado
	private Address address;
	
	private Boolean active = Boolean.TRUE;
	
	private Boolean open = Boolean.FALSE;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dateRegister;
	 
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime") //para tirar a precisão de milissegundos
	private OffsetDateTime dateUpdate;
	
	@ManyToMany //Tudo que termina com ToMany tem a estrátegia de carregamento lazy
	@JoinTable(name = "restaurant_form_payment",
			joinColumns = @JoinColumn(name = "restaurant_id"),
			inverseJoinColumns = @JoinColumn(name = "form_payment_id"))
	private Set<FormPayment> paymentMethods = new HashSet<>();
	
	@ManyToMany
	@JoinTable(name = "restaurant_user_responsible",
	        joinColumns = @JoinColumn(name = "restaurant_id"),
	        inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> responsible = new HashSet<>();
	
	@OneToMany(mappedBy = "restaurant")
	private List<Product> products = new ArrayList<>();
	
	public void active() {
		setActive(true);
	}
	
	public void deactivate() {
		setActive(false);
	}
	
	public void removeFormPayment(FormPayment formPayment) {
		getPaymentMethods().remove(formPayment);
	}
	
	public void addFormPayment(FormPayment formPayment) {
		getPaymentMethods().add(formPayment);
	}

	public void open() {
	    setOpen(true);
	}

	public void close() {
	    setOpen(false);
	} 
	
	public void removeResponsible(User user) {
		getResponsible().remove(user);
	}

	public void addResponsible(User user) {
		getResponsible().add(user);
	}
	
	public boolean acceptFormPayment(FormPayment formPayment) {
	    return getPaymentMethods().contains(formPayment);
	}

	public boolean noAcceptFormPayment(FormPayment formPayment) {
	    return !acceptFormPayment(formPayment);
	}

}
