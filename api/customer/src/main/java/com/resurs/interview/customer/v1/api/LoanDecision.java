package com.resurs.interview.customer.v1.api;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public record LoanDecision(
        @Schema(
                description = "The unique identifier of the customer",
                example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Long customerId,

        @Schema(
                description = "The decision of the loan request",
                example = "APPROVED",
                allowableValues = {"APPROVED", "DENIED"},
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Decision decision,

        @Schema(
                description = "The reason for the decision",
                example = "Credit score too low",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        String reason
) {
}
