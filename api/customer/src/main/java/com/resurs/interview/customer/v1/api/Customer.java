package com.resurs.interview.customer.v1.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import static io.swagger.v3.oas.annotations.media.Schema.*;

@Builder
public record Customer(
    @Schema(
            description = "The unique identifier of the customer",
            example = "1",
            requiredMode = RequiredMode.REQUIRED
    )
    Long id,
    @Schema(
            description = "The name of the customer",
            example = "John Doe",
            requiredMode = RequiredMode.REQUIRED
    )
    String name,
    @Schema(
            description = "The social security number of the customer",
            example = "198001012222",
            requiredMode = RequiredMode.REQUIRED
    )
    String socialSecurityNumber,
    @Schema(
            description = "The credit score of the customer",
            example = "800",
            requiredMode = RequiredMode.NOT_REQUIRED
    )
    Integer creditScore
) {
}
