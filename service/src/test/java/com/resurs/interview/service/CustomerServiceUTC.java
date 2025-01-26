package com.resurs.interview.service;

import com.resurs.interview.config.ResursConfiguration;
import com.resurs.interview.customer.v1.api.Customer;
import com.resurs.interview.customer.v1.api.CustomerCreationRequest;
import com.resurs.interview.customer.v1.api.Decision;
import com.resurs.interview.customer.v1.api.LoanDecision;
import com.resurs.interview.entity.CustomerEntity;
import com.resurs.interview.gateway.TaxCheckGateway;
import com.resurs.interview.repository.CustomerRepository;
import com.resurs.interview.transaction.v1.api.Transaction;
import com.resurs.interview.transaction.v1.api.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceUTC {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private TransactionService transactionService;

    @Mock
    private TaxCheckGateway taxCheckGateway;

    @Mock
    private ResursConfiguration resursConfiguration;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void testCreateCustomer() {
        CustomerCreationRequest request = new CustomerCreationRequest("John Doe", "123456789", 500);
        CustomerEntity savedEntity = CustomerEntity.builder()
                .id(1L)
                .name(request.name())
                .socialSecurityNumber(request.socialSecurityNumber())
                .creditScore(request.creditScore())
                .build();

        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(savedEntity);

        Customer result = customerService.createCustomer(request);

        assertNotNull(result);
        assertEquals("John Doe", result.name());
        assertEquals("123456789", result.socialSecurityNumber());
        assertEquals(500, result.creditScore());
    }

    @Test
    void testGetCustomerById_Found() {
        CustomerEntity entity = CustomerEntity.builder()
                .id(1L)
                .name("John Doe")
                .socialSecurityNumber("123456789")
                .creditScore(500)
                .build();

        when(customerRepository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<Customer> result = customerService.getCustomerById(1L);

        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().name());
    }

    @Test
    void testGetCustomerById_NotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Customer> result = customerService.getCustomerById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void testRequestLoan_Approved() {
        CustomerEntity entity = CustomerEntity.builder()
                .id(1L)
                .name("John Doe")
                .socialSecurityNumber("123456789")
                .creditScore(350)
                .build();

        when(customerRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(taxCheckGateway.getLastYearTax("123456789")).thenReturn(15000);

        LoanDecision decision = customerService.requestLoan(1L);

        assertNotNull(decision);
        assertEquals(Decision.APPROVED, decision.decision());
        assertEquals("Customer fulfills all requirements", decision.reason());
    }

    @Test
    void testRequestLoan_Denied_LowCreditScore() {
        CustomerEntity entity = CustomerEntity.builder()
                .id(1L)
                .name("John Doe")
                .socialSecurityNumber("123456789")
                .creditScore(250)
                .build();

        when(resursConfiguration.minCreditScore()).thenReturn(300);
        when(resursConfiguration.minTaxAmount()).thenReturn(10000);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(taxCheckGateway.getLastYearTax("123456789")).thenReturn(15000);

        LoanDecision decision = customerService.requestLoan(1L);

        assertNotNull(decision);
        assertEquals(Decision.DENIED, decision.decision());
        assertEquals("Credit score too low", decision.reason());
    }

    @Test
    void testRequestLoan_Denied_LowTax() {
        CustomerEntity entity = CustomerEntity.builder()
                .id(1L)
                .name("John Doe")
                .socialSecurityNumber("123456789")
                .creditScore(350)
                .build();

        when(resursConfiguration.minCreditScore()).thenReturn(300);
        when(resursConfiguration.minTaxAmount()).thenReturn(10000);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(taxCheckGateway.getLastYearTax("123456789")).thenReturn(5000);

        LoanDecision decision = customerService.requestLoan(1L);

        assertNotNull(decision);
        assertEquals(Decision.DENIED, decision.decision());
        assertEquals("Last year tax too low", decision.reason());
    }

    @Test
    void testCalculateCreditScore_NoTransactions() {
        when(transactionService.getTransactionsByCustomerId(1L)).thenReturn(List.of());

        Optional<Integer> creditScore = customerService.calculateCreditScore(1L);

        assertFalse(creditScore.isPresent());
    }

    @Test
    void testCalculateCreditScore_WithTransactions() {
        Transaction transaction1 = Transaction.builder()
                .transactionId(1L)
                .customerId(1001L)
                .amount(BigDecimal.valueOf(100))
                .transactionType(TransactionType.CREDIT)
                .build();
        Transaction transaction2 = Transaction.builder()
                .transactionId(2L)
                .customerId(1002L)
                .amount(BigDecimal.valueOf(50))
                .transactionType(TransactionType.DEBIT)
                .build();

        when(transactionService.getTransactionsByCustomerId(1L)).thenReturn(List.of(transaction1, transaction2));
        when(transactionService.getAverageTransactionAmount(1L)).thenReturn(BigDecimal.valueOf(75));

        Optional<Integer> creditScore = customerService.calculateCreditScore(1L);

        assertTrue(creditScore.isPresent());
        assertNotNull(creditScore.get());
    }
}
