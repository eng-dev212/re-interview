package com.resurs.interview.entity;

import com.resurs.interview.customer.v1.api.Customer;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String socialSecurityNumber;

    @Column
    private Integer creditScore;

    @CreationTimestamp
    @Column
    private Instant createdAt;

    @UpdateTimestamp
    @Column
    private Instant updatedAt;

    public Customer asCustomer() {
        return Customer.builder()
                .id(id)
                .name(name)
                .socialSecurityNumber(socialSecurityNumber)
                .creditScore(creditScore)
                .build();
    }
}
