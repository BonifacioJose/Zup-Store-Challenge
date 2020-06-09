package br.com.zup.api.v1.mapper;

import br.com.zup.api.v1.dto.OrderDTO;
import br.com.zup.entity.Order;
import org.springframework.util.CollectionUtils;

import java.util.stream.Collectors;

public abstract class OrderDTOMapper {

    public static Order toOrder(OrderDTO orderDTO) {
        return Order.builder()
                .identifier(orderDTO.getIdentifier())
                .customerName(orderDTO.getCustomerName())
                .customerPhone(orderDTO.getCustomerPhone())
                .products(CollectionUtils.isEmpty(orderDTO.getProducts()) ? null : orderDTO.getProducts().stream().map(ProductDTOMapper::toProduct).collect(Collectors.toList()))
                .total(orderDTO.getTotal())
                .discount(orderDTO.getDiscount())
                .productsTotal(orderDTO.getProductsTotal())
                .build();
    }

    public static OrderDTO fromOrder(Order order) {
        return OrderDTO.builder()
                .identifier(order.getIdentifier())
                .customerName(order.getCustomerName())
                .customerPhone(order.getCustomerPhone())
                .products(CollectionUtils.isEmpty(order.getProducts()) ? null : order.getProducts().stream().map(ProductDTOMapper::fromProduct).collect(Collectors.toList()))
                .total(order.getTotal())
                .discount(order.getDiscount())
                .productsTotal(order.getProductsTotal())
                .build();
    }

}
