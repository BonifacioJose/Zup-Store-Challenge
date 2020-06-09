package br.com.zup.service;

import br.com.zup.entity.Order;
import br.com.zup.entity.Product;
import br.com.zup.exception.ApiErrorException;
import br.com.zup.mock.TestScenarios;
import br.com.zup.repository.OrderRepository;
import br.com.zup.service.order.OrderService;
import br.com.zup.service.order.impl.OrderServiceImpl;
import br.com.zup.service.product.ProductService;
import br.com.zup.validator.order.OrderValidator;
import br.com.zup.validator.order.impl.OrderValidatorImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class OrderServiceTests {

    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private ProductService productService;
    @MockBean
    private OrderValidator orderValidator;
    
    private TestScenarios testScenarios = new TestScenarios();

    @Before
    public void init() {
        orderValidator = new OrderValidatorImpl();
        orderService = new OrderServiceImpl(orderRepository, productService, orderValidator);
    }
    
    @Test
    public void testCreate() throws ApiErrorException {
        Order databaseOrder = testScenarios.mockOrder();
        Order newOrder = testScenarios.mockOrder();
        databaseOrder.setIdentifier(1L);
        databaseOrder.setId("1");
        Product productOne = databaseOrder.getProducts().get(0);
        Product productTwo = databaseOrder.getProducts().get(1);
        when(orderRepository.findFirstByOrderByIdentifierDesc()).thenReturn(Optional.of(databaseOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(newOrder);
        when(productService.getProductByIdentifier(productOne.getIdentifier())).thenReturn(productOne);
        when(productService.getProductByIdentifier(productTwo.getIdentifier())).thenReturn(productTwo);
        newOrder = orderService.save(newOrder);
        assertEquals(2L, newOrder.getIdentifier().longValue());
        assertEquals(new BigDecimal(19), newOrder.getTotal());
    }

    @Test
    public void testUpdate() throws ApiErrorException {
        Order databaseOrder = testScenarios.mockOrder();
        Order saveOrder = testScenarios.mockOrder();
        databaseOrder.setIdentifier(1L);
        databaseOrder.setId("1");
        Product productOne = databaseOrder.getProducts().get(0);
        Product productTwo = databaseOrder.getProducts().get(1);
        when(orderRepository.findFirstByIdentifier(1L)).thenReturn(Optional.of(databaseOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(saveOrder);
        when(productService.getProductByIdentifier(productOne.getIdentifier())).thenReturn(productOne);
        when(productService.getProductByIdentifier(productTwo.getIdentifier())).thenReturn(productTwo);
        databaseOrder = orderService.save(saveOrder);
        assertEquals(databaseOrder.getId(), saveOrder.getId());
        assertEquals(new BigDecimal(19), saveOrder.getTotal());
    }

    @Test
    public void testGetByIdentifier() throws ApiErrorException {
        Order databaseOrder = testScenarios.mockOrder();
        databaseOrder.setIdentifier(1L);
        databaseOrder.setId("1");
        when(orderRepository.findFirstByIdentifier(1L)).thenReturn(Optional.of(databaseOrder));
        Order returnOrder = orderService.getOrderByIdentifier(1L);
        assertEquals(databaseOrder.getId(), returnOrder.getId());
    }

    @Test(expected = ApiErrorException.class)
    public void testGetByIdentifierException() throws ApiErrorException {
        Order databaseOrder = testScenarios.mockOrder();
        databaseOrder.setIdentifier(1L);
        databaseOrder.setId("1");
        orderService.getOrderByIdentifier(1L);
    }

    @Test
    public void testDeleteOrder() throws ApiErrorException {
        Order databaseOrder = testScenarios.mockOrder();
        databaseOrder.setIdentifier(1L);
        databaseOrder.setId("1");
        when(orderRepository.findFirstByIdentifier(1L)).thenReturn(Optional.of(databaseOrder));
        orderService.delete(1L);
        verify(orderRepository, times(1)).delete(databaseOrder);
    }

    @Test(expected = ApiErrorException.class)
    public void testDeleteException() throws ApiErrorException {
        Order databaseOrder = testScenarios.mockOrder();
        databaseOrder.setIdentifier(1L);
        databaseOrder.setId("1");
        orderService.delete(1L);
    }

    @Test
    public void testGetOrders() {
        List<Order> databaseOrders = testScenarios.mockOrders();

        when(orderRepository.findAll()).thenReturn(databaseOrders);

        List<Order> orders = orderService.getOrders();
        assertEquals(databaseOrders.size(), orders.size());
    }
}
