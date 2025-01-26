package com.resurs.interview.repository;

import java.util.List;
import com.resurs.interview.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    // Find transactions by customer ID
    List<TransactionEntity> findByCustomerId(Long customerId);
}