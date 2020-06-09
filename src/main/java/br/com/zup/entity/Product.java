package br.com.zup.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    private String id;
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
