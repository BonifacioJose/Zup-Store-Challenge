package br.com.zup.api.v1.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private Long identifier;
    private String name;
    private String manufacturer;
    private String description;
    private String sku;
    private BigDecimal weight;
    private BigDecimal height;
    private BigDecimal width;
    private BigDecimal depth;
    private BigDecimal price;

}
