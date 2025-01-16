package com.fiap.tc.application.gateways;

import com.fiap.tc.domain.entities.Customer;

public interface CustomerGatewaySpec {

    Customer load(String document);

}
