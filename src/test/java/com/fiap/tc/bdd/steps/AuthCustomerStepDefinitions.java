package com.fiap.tc.bdd.steps;

import com.fiap.tc.infrastructure.presentation.requests.CustomerLoginRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthCustomerStepDefinitions extends BaseScenariosStepDefinitions {
    public static final String INVALID_DOCUMENT = "11111111111";
    private boolean customerTokenValid;
    private boolean customerTokenGeneratedResult;
    private String backofficeToken;
    private String customerTokenGenerated;

    @When("send a request to generate auth token to a valid customer")
    public void sendRequestGenerateCustomerToken() {
        customerTokenGenerated = getCustomerToken();
    }

    @Then("the token of a customer was generated successfully")
    public void tokenGeneratedSuccessfully() {
        assertThat(customerTokenGenerated).isNotNull();

    }

    @Given("token to a validate customer is generated")
    public void getCustomerTokenToValidate() {
        customerTokenGenerated = getCustomerToken();
        assertThat(customerTokenGenerated).isNotNull();
    }

    @When("send a request to validate customer token")
    public void sendRequestToValidateCustomerToken() {
        customerTokenValid = isCustomerTokenValid(customerTokenGenerated);
    }

    @Then("the token of a customer was validated successfully")
    public void tokenValidatedSuccessfully() {
        assertThat(customerTokenValid).isTrue();
    }

    @When("send a request to try to validate an invalid customer token")
    public void sendRequestToValidateInvalidCustomerToken() {
        customerTokenValid = isCustomerTokenValid("test");
    }

    @Then("the customer token is invalid")
    public void customerTokenIsInvalid() {
        assertThat(customerTokenValid).isFalse();
    }

    @When("send a request to try to generate auth token to a not registered customer")
    public void tryGenerateCustomerTokenUnregisteredCustomer() {
        customerTokenGeneratedResult = isCustomerTokenGenerated();
    }

    @Then(("the customer token not generated"))
    public void customerTokenNotGenerated() {
        assertThat(customerTokenGeneratedResult).isFalse();
    }

    @When("send a request to generate token to backoffice profile")
    public void generateLoginTokenBackoffice() {
        backofficeToken = generateBackofficeToken();
    }

    @Then("the token to backoffice was generated successfully")
    public void backofficeTokenGeneratedSuccessfully() {
        assertThat(backofficeToken).isNotNull();
    }

    private boolean isCustomerTokenGenerated() {
        var request = CustomerLoginRequest.builder().document(AuthCustomerStepDefinitions.INVALID_DOCUMENT).build();
        var customerTokenResponse = testRestTemplate.postForEntity(CUSTOMER_LOGIN_URL, request, Map.class);

        return customerTokenResponse.getStatusCode().is2xxSuccessful();
    }

}
