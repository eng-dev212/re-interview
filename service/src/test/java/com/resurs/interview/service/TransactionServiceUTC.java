package com.resurs.interview.service;

import org.junit.jupiter.api.Test;

import com.resurs.interview.entity.TransactionEntity;
import com.resurs.interview.repository.TransactionRepository;
import com.resurs.interview.transaction.v1.api.Transaction;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceUTC {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void testGetTransactionsByCustomerId() {
        TransactionEntity transactionEntity1 = new TransactionEntity();
        TransactionEntity transactionEntity2 = new TransactionEntity();

        when(transactionRepository.findByCustomerId(1L)).thenReturn(List.of(transactionEntity1, transactionEntity2));

        List<Transaction> result = transactionService.getTransactionsByCustomerId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(transactionRepository, times(1)).findByCustomerId(1L);
    }

    @Test
    void testGetTransactionsByCustomerId_Empty() {
        when(transactionRepository.findByCustomerId(1L)).thenReturn(List.of());

        List<Transaction> result = transactionService.getTransactionsByCustomerId(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(transactionRepository, times(1)).findByCustomerId(1L);
    }

    @Test
    void testGetAverageTransactionAmount() {
        when(transactionRepository.getAverageTransactionAmount(1L)).thenReturn(Optional.of(BigDecimal.valueOf(100.5)));

        BigDecimal result = transactionService.getAverageTransactionAmount(1L);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(100.5).setScale(2, RoundingMode.HALF_UP), result.setScale(2, RoundingMode.HALF_UP));
        verify(transactionRepository, times(1)).getAverageTransactionAmount(1L);
    }

    @Test
    void testGetAverageTransactionAmount_NoTransactions() {
        when(transactionRepository.getAverageTransactionAmount(1L)).thenReturn(Optional.empty());

        BigDecimal result = transactionService.getAverageTransactionAmount(1L);

        assertNotNull(result);
        assertEquals(BigDecimal.ZERO, result);
        verify(transactionRepository, times(1)).getAverageTransactionAmount(1L);
    }
}
