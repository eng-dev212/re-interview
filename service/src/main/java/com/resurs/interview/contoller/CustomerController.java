package com.resurs.interview.contoller;

import com.resurs.interview.customer.v1.api.Customer;
import com.resurs.interview.customer.v1.api.CustomerApi;
import com.resurs.interview.customer.v1.api.CustomerCreationRequest;
import com.resurs.interview.customer.v1.api.LoanDecision;
import com.resurs.interview.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController implements CustomerApi {
    private final CustomerService customerService;

    @Override
    public ResponseEntity<Customer> createCustomer(@Validated CustomerCreationRequest customerCreationRequest) {
        Customer created = customerService.createCustomer(customerCreationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Override
    public ResponseEntity<Customer> getCustomer(Long customerId) {
        return customerService.getCustomerById(customerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Override
    public ResponseEntity<Integer> getCreditScore(Long customerId) {
        return customerService.getCreditScore(customerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok(0));
    }

    @Override
    public ResponseEntity<Integer> calculateCreditScore(Long customerId) {
        return customerService.calculateCreditScore(customerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok(0));
    }

    @Override
    public ResponseEntity<LoanDecision> requestLoan(Long customerId) {
        return ResponseEntity.ok(customerService.requestLoan(customerId));
    }
}
