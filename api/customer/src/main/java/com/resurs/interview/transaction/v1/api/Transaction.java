package com.resurs.interview.transaction.v1.api;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public record Transaction(

        @Schema(
                description = "The unique identifier of the transaction",
                example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Long transactionId,

        @Schema(
                description = "The unique identifier of the customer",
                example = "1001",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Long customerId,

        @Schema(
                description = "The amount of the transaction",
                example = "100.00",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        BigDecimal amount,

        @Schema(
                description = "The date of the transaction",
                example = "2021-01-01T12:00:00",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        LocalDateTime transactionDate,

        @Schema(
                description = "The type of the transaction",
                example = "CREDIT",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        TransactionType transactionType
) {
}
