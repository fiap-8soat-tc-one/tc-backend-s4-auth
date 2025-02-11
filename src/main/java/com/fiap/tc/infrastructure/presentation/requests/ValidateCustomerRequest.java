package com.fiap.tc.infrastructure.presentation.requests;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@NotEmpty
public class ValidateCustomerRequest implements Serializable {

    @NotEmpty
    @ApiModelProperty(
            value = "JWT Token",
            required = true,
            dataType = "String"
    )
    private String accessToken;
}
