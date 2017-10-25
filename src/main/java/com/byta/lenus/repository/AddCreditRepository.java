package com.byta.lenus.repository;

import com.byta.lenus.domain.AddCredit;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AddCredit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddCreditRepository extends JpaRepository<AddCredit, Long> {

}
