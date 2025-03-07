package com.fiap.tc.infrastructure.services;

import com.fiap.tc.domain.entities.Customer;
import com.fiap.tc.infrastructure.core.config.RestClientBackofficeConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;
import java.util.Optional;

import static java.lang.String.format;

@Service
public class CustomerServiceClient {
    static final String RESOURCE = "/api/private/v1/customers/%s";
    private final WebClient webClientBackoffice;
    private final RestClientBackofficeConfig restClientConfig;
    private final AuthTokenManager authTokenManager;

    public CustomerServiceClient(WebClient webClientBackoffice,
                                 RestClientBackofficeConfig restClientConfig,
                                 AuthTokenManager authTokenManager) {
        this.webClientBackoffice = webClientBackoffice;
        this.restClientConfig = restClientConfig;
        this.authTokenManager = authTokenManager;
    }

    @Cacheable(value = "customer", key = "#document")
    public Optional<Customer> load(String document) {

        try {
            return Optional.ofNullable(this.webClientBackoffice.method(HttpMethod.GET).uri(format(RESOURCE, document))
                    .headers(header -> {
                        header.set("Authorization", format("Bearer %s", authTokenManager.getToken()));
                        header.set("Accept", MediaType.APPLICATION_JSON_VALUE);
                    })
                    .retrieve()
                    .bodyToMono(Customer.class)
                    .retry(restClientConfig.getRetry())
                    .timeout(Duration.ofSeconds(restClientConfig.getTimeout()))
                    .block());
        } catch (WebClientResponseException exception) {
            if (exception.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return Optional.empty();
            }
            throw exception;

        }
    }
}
