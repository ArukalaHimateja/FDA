package com.fda.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fda.app.model.Billing;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long>{

}
