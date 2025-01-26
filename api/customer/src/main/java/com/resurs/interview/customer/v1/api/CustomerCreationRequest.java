package com.resurs.interview.customer.v1.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.*;

@JsonNaming(value = SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public record CustomerCreationRequest(
        @Schema(description = "Customer name", example = "John Doe")
        @NotBlank
        String name,

        @Schema(description = "Customer social security number", example = "198001012222")
        @NotBlank
        String socialSecurityNumber,

        @Schema(description = "Customer credit score", example = "800")
        @PositiveOrZero
        Integer creditScore
) {
}
