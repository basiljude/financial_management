package com.byta.lenus.repository;

import com.byta.lenus.domain.DebitTransactionType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DebitTransactionType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DebitTransactionTypeRepository extends JpaRepository<DebitTransactionType, Long> {

}
