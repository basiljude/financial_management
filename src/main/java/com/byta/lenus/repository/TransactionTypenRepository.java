package com.byta.lenus.repository;

import com.byta.lenus.domain.TransactionTypen;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TransactionTypen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionTypenRepository extends JpaRepository<TransactionTypen, Long> {

}
