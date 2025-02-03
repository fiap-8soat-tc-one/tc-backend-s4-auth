package com.fiap.tc.infrastructure.presentation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.tc.infrastructure.presentation.requests.CustomerLoginRequest;
import com.fiap.tc.infrastructure.presentation.requests.ValidateCustomerRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static com.fiap.tc.util.TestUtils.readResourceFileAsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class CustomerLoginControllerIT {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void customerLoginTest() throws Exception {
        customerLogin();
    }

    @Test
    public void validateLogin() throws Exception {
        var loginResponse = customerLogin();
        var accessToken = loginResponse.get("access_token");
        var validateCustomerRequest = readResourceFileAsString(ValidateCustomerRequest.class,
                "validate_customer_login.json");

        var request = String.format(validateCustomerRequest, accessToken);
        mockMvc.perform(post("/api/public/v1/oauth/token/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                )
                .andExpect(status().isOk());
        ;
    }

    @Test
    public void validateLogin_ShouldReturnForbidden_WhenInvalidToken() throws Exception {
        mockMvc.perform(post("/api/public/v1/oauth/token/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readResourceFileAsString(ValidateCustomerRequest.class,
                                "validate_customer_login_invalid_token.json"))
                )
                .andExpect(status().isForbidden())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    private Map<String, String> customerLogin() throws Exception {
        String responseJson = mockMvc.perform(post("/api/public/v1/oauth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readResourceFileAsString(CustomerLoginRequest.class, "customer_login_request.json"))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.access_token").exists())
                .andExpect(jsonPath("$.profile").exists())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(responseJson, Map.class);
    }


}