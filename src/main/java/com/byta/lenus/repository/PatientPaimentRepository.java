package com.byta.lenus.repository;

import com.byta.lenus.domain.PatientPaiment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PatientPaiment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientPaimentRepository extends JpaRepository<PatientPaiment, Long> {

}
