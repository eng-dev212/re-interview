package com.resurs.interview.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import com.resurs.interview.config.ResursConfiguration;
import com.resurs.interview.customer.v1.api.Customer;
import com.resurs.interview.customer.v1.api.CustomerCreationRequest;
import com.resurs.interview.customer.v1.api.Decision;
import com.resurs.interview.customer.v1.api.LoanDecision;
import com.resurs.interview.entity.CustomerEntity;
import com.resurs.interview.exception.CustomerNotFoundException;
import com.resurs.interview.gateway.AccountBalanceResponse;
import com.resurs.interview.gateway.TaxCheckGateway;
import com.resurs.interview.gateway.ThirdPartyAPIService;
import com.resurs.interview.model.Balance;
import com.resurs.interview.repository.CustomerRepository;
import com.resurs.interview.transaction.v1.api.Transaction;
import com.resurs.interview.transaction.v1.api.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final TransactionService transactionService;
    private final ThirdPartyAPIService thirdPartyAPIService;
    private final TaxCheckGateway taxCheckGateway;
    private final ResursConfiguration resursConfiguration;

    public Customer createCustomer(CustomerCreationRequest customerCreationRequest) {
        return customerRepository.save(
                CustomerEntity.builder()
                        .name(customerCreationRequest.name())
                        .socialSecurityNumber(customerCreationRequest.socialSecurityNumber())
                        .creditScore(customerCreationRequest.creditScore())
                        .build()
        ).asCustomer();
    }

    public Optional<Customer> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId).map(CustomerEntity::asCustomer);
    }

    @Cacheable("creditScore")
    public Optional<Integer> getCreditScore(Long customerId) {
        CustomerEntity customerEntity = customerRepository.findById(customerId).orElseThrow(
                () -> new CustomerNotFoundException(customerId)
        );

        return Optional.ofNullable(customerEntity.getCreditScore());
    }

    @CacheEvict(value = "creditScore", key = "#customerId")
    public Optional<Integer> calculateCreditScore(Long customerId) {
        List<Transaction> customerTransactions = transactionService.getTransactionsByCustomerId(customerId);

        if (customerTransactions.isEmpty()) {
            return Optional.empty();
        }

        BigDecimal averageTransactionAmount = transactionService.getAverageTransactionAmount(customerId);

        Balance balance = calculateBalance(customerTransactions);

        Integer creditScore = (int) Math.ceil(
                300 +
                (balance.normalizedBalance() * 350) +
                (averageTransactionAmount.doubleValue() * 0.05) +
                (customerTransactions.size() * 20));

        return Optional.of(creditScore);
    }

    private Balance calculateBalance(List<Transaction> customerTransactions) {
        BigDecimal balance = BigDecimal.ZERO;
        BigDecimal minBalance = BigDecimal.valueOf(Double.MAX_VALUE);
        BigDecimal maxBalance = BigDecimal.valueOf(Double.MIN_VALUE);

        for (Transaction transaction : customerTransactions) {
            if (transaction.transactionType() == TransactionType.DEBIT) {
                balance = balance.subtract(transaction.amount());
            } else {
                balance = balance.add(transaction.amount());
            }
            minBalance = minBalance.min(balance);
            maxBalance = maxBalance.max(balance);
        }

        double normalizedBalance = 0;
        if (maxBalance.compareTo(minBalance) > 0) {
            normalizedBalance = balance.subtract(minBalance).divide(maxBalance.subtract(minBalance), 2, RoundingMode.HALF_UP).doubleValue();
        }

        return Balance.builder()
                .balance(balance)
                .normalizedBalance(normalizedBalance)
                .minBalance(minBalance)
                .maxBalance(maxBalance)
                .build();
    }

    public LoanDecision requestLoan(Long customerId) {
        return customerRepository.findById(customerId)
                .map(customerEntity -> {
                    int lastYearTax = taxCheckGateway.getLastYearTax(customerEntity.getSocialSecurityNumber());
                    return shallWeGiveALoan(lastYearTax, customerEntity.getCreditScore(), customerId);
                }).orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    private LoanDecision shallWeGiveALoan(int lastYearTax, Integer creditScore, Long customerId) {
        int minCreditScore = resursConfiguration.minCreditScore();
        int minTaxAmount = resursConfiguration.minTaxAmount();

        if (creditScore == null) {
            return LoanDecision.builder()
                    .customerId(customerId)
                    .decision(Decision.DENIED)
                    .reason("Credit score not available")
                    .build();
        }

        if (creditScore < minCreditScore) {
            return LoanDecision.builder()
                    .customerId(customerId)
                    .decision(Decision.DENIED)
                    .reason("Credit score too low")
                    .build();
        }

        if (lastYearTax < minTaxAmount) {
            return LoanDecision.builder()
                    .customerId(customerId)
                    .decision(Decision.DENIED)
                    .reason("Last year tax too low")
                    .build();
        }

        return LoanDecision.builder()
                .customerId(customerId)
                .decision(Decision.APPROVED)
                .reason("Customer fulfills all requirements")
                .build();
    }
}
