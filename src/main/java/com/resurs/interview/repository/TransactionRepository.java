package com.resurs.interview.repository;

import java.util.List;
import com.resurs.interview.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Find transactions by customer ID
    List<Transaction> findByCustomerId(Long customerId);
}