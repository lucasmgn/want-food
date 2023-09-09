package com.wantfood.aplication.domain.repository;

import com.wantfood.aplication.domain.model.FormPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;

public interface FormPaymentRepository extends JpaRepository<FormPayment, Long>{
	
	@Query("select max(dateUpdate) from FormPayment")
	OffsetDateTime getDateUpdate();
	
	@Query("select dateUpdate from FormPayment where id = :formPaymentId")
	OffsetDateTime getDataUpdateById(Long formPaymentId);
}	
