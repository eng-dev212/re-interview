package com.resurs.interview.contoller;

import com.resurs.interview.customer.v1.api.Customer;
import com.resurs.interview.customer.v1.api.CustomerApi;
import com.resurs.interview.customer.v1.api.CustomerCreationRequest;
import com.resurs.interview.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController implements CustomerApi {
    private final CustomerService customerService;

    @Override
    public ResponseEntity<Customer> createCustomer(CustomerCreationRequest customerCreationRequest) {
        Customer created = customerService.createCustomer(customerCreationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Override
    public ResponseEntity<Customer> getCustomer(Long customerId) {
        return null;
    }

    @Override
    public ResponseEntity<Integer> getCreditScore(Long customerId) {
        return null;
    }

    @Override
    public ResponseEntity<String> requestLoan(Long customerId) {
        return null;
    }
}
