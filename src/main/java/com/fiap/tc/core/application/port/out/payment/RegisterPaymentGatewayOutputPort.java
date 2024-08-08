package com.fiap.tc.core.application.port.out.payment;

import com.fiap.tc.core.domain.model.OrderGatewayPayment;
import com.fiap.tc.adapters.driver.presentation.requests.OrderPaymentRequest;

import java.math.BigDecimal;
import java.util.UUID;

public interface RegisterPaymentGatewayOutputPort {
    OrderGatewayPayment register(OrderPaymentRequest orderPaymentRequest, UUID orderId, BigDecimal total);
}
