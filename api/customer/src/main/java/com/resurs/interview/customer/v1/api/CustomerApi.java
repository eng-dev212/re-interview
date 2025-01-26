package com.resurs.interview.customer.v1.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface CustomerApi {

    String PREFIX = "/api/v1/customer/";

    String CREATE_CUSTOMER = PREFIX;
    String GET_CUSTOMER = PREFIX + "{customerId}";
    String GET_CREDIT_SCORE = PREFIX + "{customerId}/score";
    String CALCULATE_CREDIT_SCORE = PREFIX + "{customerId}/creditScore/calculate";

    @Operation(summary = "Create a new customer")
    @ApiResponse(
            responseCode = "201",
            description = "Customer created",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Customer.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "409", description = "Customer already exists")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping(path = CREATE_CUSTOMER,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Customer> createCustomer(@RequestBody CustomerCreationRequest customerCreationRequest);

    @Operation(summary = "Get a customer")
    @ApiResponse(
            responseCode = "200",
            description = "Customer found",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Customer.class)))
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(path = GET_CUSTOMER,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Customer> getCustomer(@PathVariable Long customerId);

    @Operation(summary = "Get a customer's credit score")
    @ApiResponse(responseCode = "200", description = "Credit score found")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(path = GET_CREDIT_SCORE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Integer> getCreditScore(@PathVariable Long customerId);

    @Operation(summary = "Calculate a customer's credit score")
    @ApiResponse(responseCode = "200", description = "Calculated credit score")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping(path = CALCULATE_CREDIT_SCORE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Integer> calculateCreditScore(@PathVariable Long customerId);

    @Operation(summary = "Request a loan")
    @ApiResponse(responseCode = "200")
    @PostMapping(path = PREFIX + "{customerId}/loan",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<LoanDecision> requestLoan(@PathVariable Long customerId);

}
