package com.resurs.interview.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

import com.resurs.interview.transaction.v1.api.Transaction;
import com.resurs.interview.transaction.v1.api.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @Column
    @UpdateTimestamp
    private Instant createdAt;

    @Column
    private Instant updatedAt;

    public Transaction asTransaction() {
        return Transaction.builder()
                .transactionId(transactionId)
                .customerId(customerId)
                .amount(amount)
                .transactionDate(transactionDate)
                .transactionType(transactionType)
                .build();
    }
}
