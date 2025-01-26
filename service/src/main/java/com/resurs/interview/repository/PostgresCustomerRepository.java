package com.resurs.interview.repository;

import java.util.Optional;
import com.resurs.interview.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

@Repository
public class PostgresCustomerRepository implements CustomerRepository {

    @Override
    public Optional<CustomerEntity> findBySocialSecurityNumber(String ssn) {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public CustomerEntity save(CustomerEntity customerEntity) {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public Optional<CustomerEntity> findById(Long customerId) {
        return Optional.empty();
    }
}
