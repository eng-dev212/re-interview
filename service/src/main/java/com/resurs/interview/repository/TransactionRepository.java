package com.resurs.interview.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.resurs.interview.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findByCustomerId(Long customerId);

    @Query("SELECT AVG(t.amount) FROM TransactionEntity t WHERE t.customerId = :customerId")
    Optional<BigDecimal> getAverageTransactionAmount(Long customerId);
}