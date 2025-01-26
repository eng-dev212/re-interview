package com.resurs.interview.exception;

public class CustomerNotFoundException extends RuntimeException {

        public CustomerNotFoundException(long customerId) {
            super("Customer with ID %d not found".formatted(customerId));
        }
}

