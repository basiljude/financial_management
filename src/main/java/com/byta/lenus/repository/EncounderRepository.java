package com.byta.lenus.repository;

import com.byta.lenus.domain.Encounder;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Encounder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EncounderRepository extends JpaRepository<Encounder, Long> {

}
