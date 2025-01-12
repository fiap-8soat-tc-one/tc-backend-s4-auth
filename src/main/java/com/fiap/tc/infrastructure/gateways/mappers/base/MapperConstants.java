package com.fiap.tc.infrastructure.gateways.mappers.base;

import com.fiap.tc.infrastructure.gateways.mappers.CustomerMapper;
import org.mapstruct.factory.Mappers;

public class MapperConstants {

    private MapperConstants() {
    }

    public static final CustomerMapper CUSTOMER_MAPPER = Mappers.getMapper(CustomerMapper.class);

}
