package com.byta.lenus.repository;

import com.byta.lenus.domain.DebitCashDesk;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DebitCashDesk entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DebitCashDeskRepository extends JpaRepository<DebitCashDesk, Long> {

}
