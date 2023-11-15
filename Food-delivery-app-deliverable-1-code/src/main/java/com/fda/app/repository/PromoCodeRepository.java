package com.fda.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fda.app.model.PromoCode;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, Long> {

	boolean existsByPromoCode(String code);

	PromoCode findByPromoCode(String promoCode);

}
