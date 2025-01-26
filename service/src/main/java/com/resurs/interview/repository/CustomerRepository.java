package com.resurs.interview.repository;

import java.util.Optional;
import com.resurs.interview.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findBySocialSecurityNumber(String ssn);
}
