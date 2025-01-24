package com.resurs.interview.repository;

import java.util.Optional;
import com.resurs.interview.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CustomerRepository {
    Optional<Customer> findBySocialSecurityNumber(String ssn);

    Customer save(Customer customer);

    Optional<Customer> findById(Long customerId);
}
