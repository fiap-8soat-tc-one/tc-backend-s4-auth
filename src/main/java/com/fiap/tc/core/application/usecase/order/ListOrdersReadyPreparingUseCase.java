package com.fiap.tc.core.application.usecase.order;

import com.fiap.tc.core.application.port.in.order.ListOrdersReadyPreparingInputPort;
import com.fiap.tc.core.domain.model.enums.OrderStatus;
import com.fiap.tc.adapters.driver.presentation.response.OrderListResponse;
import com.fiap.tc.core.application.port.out.order.ListOrdersReadyPreparingOutputPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ListOrdersReadyPreparingUseCase implements ListOrdersReadyPreparingInputPort {

    private final ListOrdersReadyPreparingOutputPort listOrdersReadyPreparingOutputPort;

    public ListOrdersReadyPreparingUseCase(ListOrdersReadyPreparingOutputPort listOrdersOutputPort) {
        this.listOrdersReadyPreparingOutputPort = listOrdersOutputPort;
    }

    @Override
    public Page<OrderListResponse> list(Pageable pageable) {
        return listOrdersReadyPreparingOutputPort.list(List.of(OrderStatus.PREPARING, OrderStatus.READY), pageable);
    }
}


