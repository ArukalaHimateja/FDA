package com.fda.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fda.app.model.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

}
