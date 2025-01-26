package com.resurs.interview.gateway;

import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class MockedThirdPartyApiService implements ThirdPartyAPIService {
    private final Random random = new Random();

    public AccountBalanceResponse getCustomerAccountBalance(String accountId) {
        return new AccountBalanceResponse(accountId, random.nextInt(100000), "SEK", LocalDateTime.now().minusMinutes(10));
    }
}
