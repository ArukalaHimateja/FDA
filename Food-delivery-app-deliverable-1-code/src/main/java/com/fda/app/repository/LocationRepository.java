package com.fda.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fda.app.model.GLocation;

@Repository
public interface LocationRepository extends JpaRepository<GLocation, Long> {

}
