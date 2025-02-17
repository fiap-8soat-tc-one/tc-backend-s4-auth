package com.fiap.tc.application.usecases.customer;

import com.fiap.tc.application.gateways.CustomerGatewaySpec;
import com.fiap.tc.domain.entities.Customer;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class LoadCustomerUseCase {

    private final CustomerGatewaySpec customerGateway;

    public LoadCustomerUseCase(CustomerGatewaySpec customerGateway) {
        this.customerGateway = customerGateway;
    }

    public Customer load(String document) {
        return customerGateway.load(document);
    }
}
