package com.resurs.interview.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "resurs")
public record ResursConfiguration(
        Integer minCreditScore,
        Integer minTaxAmount
) {

}
