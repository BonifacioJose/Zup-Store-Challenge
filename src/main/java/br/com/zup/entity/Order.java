package br.com.zup.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    private String id;
    private Long identifier;
    private List<Product> products;
    private String customerName;
    private String customerPhone;
    private BigDecimal discount;
    private BigDecimal productsTotal;
    private BigDecimal total;

}
