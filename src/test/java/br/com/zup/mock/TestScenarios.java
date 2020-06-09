package br.com.zup.mock;

import br.com.zup.entity.Order;
import br.com.zup.entity.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestScenarios {

    public Product mockProduct() {
        return Product.builder()
                .name("Notebook Acer")
                .description("test")
                .sku("111111")
                .manufacturer("Acer")
                .price(BigDecimal.TEN)
                .depth(BigDecimal.ONE)
                .width(BigDecimal.ONE)
                .weight(BigDecimal.ONE)
                .height(BigDecimal.ONE)
                .build();
    }

    public List<Product> mockProducts() {
        Product databaseProductOne = mockProduct();
        databaseProductOne.setIdentifier(1L);
        databaseProductOne.setId("1");
        Product databaseProductTwo = mockProduct();
        databaseProductTwo.setIdentifier(2L);
        databaseProductTwo.setId("2");
        List<Product> databaseProducts = new ArrayList<>();
        databaseProducts.add(databaseProductOne);
        databaseProducts.add(databaseProductTwo);
        return databaseProducts;
    }

    public Order mockOrder() {
        return Order.builder()
                .customerName("José Bonifácio")
                .customerPhone("99999999")
                .discount(BigDecimal.ONE)
                .products(mockProducts())
                .build();
    }

    public List<Order> mockOrders() {
        Order databaseOrderOne = mockOrder();
        databaseOrderOne.setIdentifier(1L);
        databaseOrderOne.setId("1");
        Order databaseOrderTwo = mockOrder();
        databaseOrderTwo.setIdentifier(2L);
        databaseOrderTwo.setId("2");
        List<Order> databaseOrders = new ArrayList<>();
        databaseOrders.add(databaseOrderOne);
        databaseOrders.add(databaseOrderTwo);
        return databaseOrders;
    }
}
