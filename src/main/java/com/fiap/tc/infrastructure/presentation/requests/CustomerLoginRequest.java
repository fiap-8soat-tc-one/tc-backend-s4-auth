package com.fiap.tc.infrastructure.presentation.requests;

import br.com.caelum.stella.bean.validation.CPF;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerLoginRequest {

    @CPF(message = "Invalid document")
    @ApiModelProperty(
            value = "CustomerDocument",
            example = "65750888053",
            dataType = "String"
    )
    private String document;
}
