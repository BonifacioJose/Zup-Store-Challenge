package br.com.zup.api.v1.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    private Long identifier;
    private List<ProductDTO> products;
    private String customerName;
    private String customerPhone;
    private BigDecimal discount;
    private BigDecimal productsTotal;
    private BigDecimal total;

}
