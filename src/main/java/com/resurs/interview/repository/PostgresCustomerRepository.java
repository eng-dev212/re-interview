package com.resurs.interview.repository;

import java.util.Optional;
import com.resurs.interview.entity.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

@Repository
public class PostgresCustomerRepository implements CustomerRepository {

    @Override
    public Optional<Customer> findBySocialSecurityNumber(String ssn) {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public Customer save(Customer customer) {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public Optional<Customer> findById(Long customerId) {
        return Optional.empty();
    }
}
