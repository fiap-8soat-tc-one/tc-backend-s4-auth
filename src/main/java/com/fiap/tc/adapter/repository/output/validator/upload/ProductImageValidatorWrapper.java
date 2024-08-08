package com.fiap.tc.adapter.repository.output.validator.upload;

import com.fiap.tc.adapter.repository.entity.ProductEntity;
import com.fiap.tc.core.domain.model.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageValidatorWrapper {
    private Integer uploadListSize;
    private ProductEntity productEntity;
    private ProductImage productImage;
}
