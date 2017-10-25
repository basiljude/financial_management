package com.byta.lenus.repository;

import com.byta.lenus.domain.AddDebit;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AddDebit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddDebitRepository extends JpaRepository<AddDebit, Long> {

}
