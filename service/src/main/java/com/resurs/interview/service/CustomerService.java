package com.resurs.interview.service;

import java.util.Optional;

import com.resurs.interview.customer.v1.api.Customer;
import com.resurs.interview.customer.v1.api.CustomerCreationRequest;
import com.resurs.interview.entity.CustomerEntity;
import com.resurs.interview.gateway.TaxCheckGateway;
import com.resurs.interview.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final TaxCheckGateway taxCheckGateway;

    public Customer createCustomer(CustomerCreationRequest customerCreationRequest) {
        return customerRepository.save(
                CustomerEntity.builder()
                        .name(customerCreationRequest.name())
                        .socialSecurityNumber(customerCreationRequest.socialSecurityNumber())
                        .creditScore(customerCreationRequest.creditScore())
                        .build()
        ).asCustomer();
    }

    public Optional<CustomerEntity> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    public Optional<Integer> getCreditScore(Long customerId) {

        return customerRepository.findById(customerId).map(CustomerEntity::getCreditScore);
    }

    public boolean requestLoan(Long customerId) {
        return customerRepository.findById(customerId)
                .map(customerEntity -> {
                    int lastYearTax = taxCheckGateway.getLastYearTax(customerEntity.getSocialSecurityNumber());
                    // Call some advanced formula like "shallWeGiveALoan()"
                    return shallWeGiveALoan(lastYearTax, customerEntity.getCreditScore());
                }).orElse(false);
    }

    private boolean shallWeGiveALoan(int lastYearTax, Integer creditScore) {
        //fixme
        return false;
    }
}
