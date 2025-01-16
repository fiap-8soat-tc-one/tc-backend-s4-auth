package com.fiap.tc.infrastructure.presentation.mappers.base;

import com.fiap.tc.infrastructure.presentation.mappers.CustomerResponseMapper;
import org.mapstruct.factory.Mappers;

public class MapperConstants {

    private MapperConstants() {
    }

    public static final CustomerResponseMapper CUSTOMER_MAPPER = Mappers.getMapper(CustomerResponseMapper.class);


}

