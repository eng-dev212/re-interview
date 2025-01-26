package com.resurs.interview.customer.v1.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface CustomerApi {

    String PREFIX = "/api/v1/customer/";

    String CREATE_CUSTOMER = PREFIX;
    String GET_CUSTOMER = PREFIX + "{customerId}";
    String GET_CREDIT_SCORE = PREFIX + "{customerId}/creditScore";

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
    @PostMapping(path = GET_CUSTOMER,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Customer> getCustomer(@PathVariable Long customerId);

    @Operation(summary = "Get a customer's credit score")
    @ApiResponse(responseCode = "200", description = "Credit score found")
    @ApiResponse(responseCode = "404", description = "Credit score not found")
    @ApiResponse(responseCode = "422", description = "Invalid customer ID")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping(path = GET_CREDIT_SCORE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Integer> getCreditScore(@PathVariable Long customerId);

    @Operation(summary = "Request a loan")
    @ApiResponse(responseCode = "200")
    @PostMapping(path = PREFIX + "{customerId}/loan",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> requestLoan(@PathVariable Long customerId);

}
