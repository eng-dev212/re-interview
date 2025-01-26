package com.resurs.interview.contoller;

import com.resurs.interview.service.TransactionService;
import com.resurs.interview.transaction.v1.api.Transaction;
import com.resurs.interview.transaction.v1.api.TransactionApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransactionController implements TransactionApi {

    private final TransactionService transactionService;

    @Override
    public ResponseEntity<List<Transaction>> getTransactions(Long customerId) {
        return ResponseEntity.ok(transactionService.getTransactionsByCustomerId(customerId));
    }

    @Override
    public ResponseEntity<BigDecimal> getAverageTransactionAmount(Long customerId) {
        return ResponseEntity.ok(transactionService.getAverageTransactionAmount(customerId));
    }
}
