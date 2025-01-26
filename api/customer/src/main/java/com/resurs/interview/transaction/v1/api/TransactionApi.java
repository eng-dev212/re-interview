package com.resurs.interview.transaction.v1.api;

import com.resurs.interview.customer.v1.api.Customer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionApi {

    String PREFIX = "/api/v1/transactions/";

    String GET_TRANSACTIONS = PREFIX + "customer/{customerId}";
    String GET_AVERAGE_TRANSACTIONS = PREFIX + "customer/{customerId}/average";

    @Operation(summary = "Get transaction by customer")
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Transaction.class)))
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(path = GET_TRANSACTIONS,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Transaction>> getTransactions(@PathVariable Long customerId);

    @Operation(summary = "Get average transaction amount by customer")
    @ApiResponse(responseCode = "200", description = "The average transaction amount")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(path = GET_AVERAGE_TRANSACTIONS,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BigDecimal> getAverageTransactionAmount(@PathVariable Long customerId);

}
