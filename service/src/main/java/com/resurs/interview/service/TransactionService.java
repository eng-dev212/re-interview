package com.resurs.interview.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import com.resurs.interview.entity.TransactionEntity;
import com.resurs.interview.repository.TransactionRepository;
import com.resurs.interview.transaction.v1.api.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {


    private final TransactionRepository transactionRepository;

    /**
     * Get all transactions for a specific customer.
     * @param customerId the ID of the customer.
     * @return list of transactions for that customer.
     */
    public List<Transaction> getTransactionsByCustomerId(Long customerId) {
        return transactionRepository.findByCustomerId(customerId).stream()
                .map(TransactionEntity::asTransaction)
                .toList();
    }

    /**
     * Get the average transaction amount for the customer.
     * @param customerId the ID of the customer.
     * @return average transaction amount or 0 if no transactions are found.
     */
    public BigDecimal getAverageTransactionAmount(Long customerId) {
       return transactionRepository.getAverageTransactionAmount(customerId).orElse(BigDecimal.ZERO);
    }
}
