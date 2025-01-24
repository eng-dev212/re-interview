package com.resurs.interview.service;

import java.util.Optional;
import com.resurs.interview.entity.Customer;
import com.resurs.interview.gateway.TaxCheckGateway;
import com.resurs.interview.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final TaxCheckGateway taxCheckGateway;

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    public Optional<Integer> getCreditScore(Long customerId) {
        return customerRepository.findById(customerId).map(Customer::getCreditScore);
    }

    public boolean requestLoan(Long customerId) {
        return customerRepository.findById(customerId)
                .map(customer -> {
                    int lastYearTax = taxCheckGateway.getLastYearTax(customer.getSocialSecurityNumber());
                    // Call some advanced formula like "shallWeGiveALoan()"
                    return shallWeGiveALoan(lastYearTax, customer.getCreditScore());
                }).orElse(false);
    }

    private boolean shallWeGiveALoan(int lastYearTax, Integer creditScore) {
        //fixme
        return false;
    }
}
