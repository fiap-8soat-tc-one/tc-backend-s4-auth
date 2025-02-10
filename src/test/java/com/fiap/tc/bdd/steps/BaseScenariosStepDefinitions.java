package com.fiap.tc.bdd.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.tc.infrastructure.core.config.RestClientOAuthConfig;
import com.fiap.tc.infrastructure.dto.AuthDetail;
import com.fiap.tc.infrastructure.presentation.requests.CustomerLoginRequest;
import com.fiap.tc.infrastructure.presentation.requests.ValidateCustomerRequest;
import com.fiap.tc.infrastructure.services.AuthLoginClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Map;
import java.util.Objects;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("ALL")
public class BaseScenariosStepDefinitions {

    @LocalServerPort
    int port;
    public static final String URL_BACKOFFICE_LOGIN = "http://localhost:8081/%s";
    public static final String BACKOFFICE_LOGIN_RESOURCE = "oauth/token";
    public static final String BACKOFFICE_LOGIN_TEMPLATE = "username=%s&password=%s&grant_type=password";


    public static final String URL_UPDATE_ORDER_TEMPLATE = "%s:%s/%s";
    public static final String ORDER_STATUS_RESOURCE = "api/private/v1/orders/status";
    public static final String GET_ORDER_RESOURCE = "api/private/v1/orders/%s";

    public static final String CUSTOMER_LOGIN_URL = "http://localhost:8081/api/public/v1/oauth/token";
    public static final String CUSTOMER_DOCUMENT = "88404071039";

    public static final String CUSTOMER_TOKEN_VALIDATE_URL = "http://localhost:8081/api/public/v1/oauth/token/validate";

    @Autowired
    private RestClientOAuthConfig restClientConfig;
    @Autowired
    protected TestRestTemplate testRestTemplate;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected AuthLoginClient authLoginClient;

    private String backofficeToken;
    private String customerToken;

    protected String generateBackofficeToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", format("Basic %s", restClientConfig.getClientId()));

        HttpEntity<String> request = new HttpEntity<>(
                format(BACKOFFICE_LOGIN_TEMPLATE, restClientConfig.getUserName(),
                        restClientConfig.getPassword()), headers);
        var url = String.format(URL_BACKOFFICE_LOGIN, BACKOFFICE_LOGIN_RESOURCE);
        ResponseEntity<AuthDetail> response = testRestTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                AuthDetail.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getAccessToken()).isNotNull();
        return response.getBody().getAccessToken();
    }


    protected String getCustomerToken() {
        if (Objects.isNull(customerToken)) {
            var request = CustomerLoginRequest.builder().document(CUSTOMER_DOCUMENT).build();
            var customerTokenResponse = testRestTemplate.postForEntity(CUSTOMER_LOGIN_URL, request, Map.class);
            assertThat(customerTokenResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(customerTokenResponse.getBody()).isInstanceOf(Map.class);
            assertThat(requireNonNull(customerTokenResponse.getBody()).get("access_token")).isNotNull();

            return (String) customerTokenResponse.getBody().get("access_token");
        }
        return customerToken;

    }

    protected boolean isCustomerTokenValid(String customerToken) {
        var request = ValidateCustomerRequest.builder().accessToken(customerToken).build();
        var validateTokenResponse = testRestTemplate.postForEntity(CUSTOMER_TOKEN_VALIDATE_URL, request, String.class);

        return validateTokenResponse.getStatusCode().is2xxSuccessful();
    }

    protected String getBackofficeToken() {
        if (isNull(backofficeToken)) {
            var authDetail = authLoginClient.execute();
            assertThat(authDetail).isNotNull();
            assertThat(authDetail.getAccessToken()).isNotNull();
            return authDetail.getAccessToken();
        }
        return backofficeToken;

    }

    protected HttpHeaders getCustomerTokenHttpHeaders() {
        var headers = new HttpHeaders();
        headers.set("X-Authorization-Token", getCustomerToken());
        return headers;
    }

    protected HttpHeaders getBackofficeTokenHttpHeaders() {
        var headers = new HttpHeaders();
        headers.set("Authorization", format("Bearer %s", getBackofficeToken()));
        return headers;
    }


}
