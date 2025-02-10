# language: en
Feature: Tech Challenge App Auth

  Scenario: Generate login token to a customer
    When send a request to generate auth token to a valid customer
    Then the token of a customer was generated successfully

  Scenario: Validate a login token of a customer
    Given token to a validate customer is generated
    When send a request to validate customer token
    Then the token of a customer was validated successfully

  Scenario: Validate a login token of a invalid token
    When send a request to try to validate an invalid customer token
    Then the customer token is invalid

  Scenario: Try to generate a login token to a not registered customer
    When send a request to try to generate auth token to a not registered customer
    Then the customer token not generated

  Scenario: Generate login token to backoffice profile
    When send a request to generate token to backoffice profile
    Then the token to backoffice was generated successfully
