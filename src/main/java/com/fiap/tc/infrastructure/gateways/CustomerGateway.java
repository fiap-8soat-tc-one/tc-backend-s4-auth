package com.fiap.tc.infrastructure.gateways;

import com.fiap.tc.application.gateways.CustomerGatewaySpec;
import com.fiap.tc.domain.entities.Customer;
import com.fiap.tc.domain.exceptions.NotFoundException;
import com.fiap.tc.infrastructure.services.CustomerServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomerGateway implements CustomerGatewaySpec {
    private final CustomerServiceClient customerServiceClient;

    @Override
    public Customer load(String document) {
        return customerServiceClient.load(document).orElseThrow(() -> new NotFoundException(
                format("Customer with document %s not found!", document)));
    }
}
