package com.resurs.interview.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record Balance(
        BigDecimal balance,
        double normalizedBalance,
        BigDecimal minBalance,
        BigDecimal maxBalance
) {
}
