package com.resurs.interview.repository;

import java.util.Optional;
import com.resurs.interview.entity.CustomerEntity;

public interface CustomerRepository {
    Optional<CustomerEntity> findBySocialSecurityNumber(String ssn);

    CustomerEntity save(CustomerEntity customerEntity);

    Optional<CustomerEntity> findById(Long customerId);
}
