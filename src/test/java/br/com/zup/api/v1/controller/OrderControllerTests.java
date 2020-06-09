package br.com.zup.api.v1.controller;

import br.com.zup.api.v1.mapper.OrderDTOMapper;
import br.com.zup.entity.Order;
import br.com.zup.mock.TestScenarios;
import br.com.zup.service.order.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class OrderControllerTests {
    private OrderController orderController;

    @MockBean
    private OrderService orderService;

    private TestScenarios testScenarios = new TestScenarios();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        orderController = new OrderController(orderService);
    }

    @Test
    public void testGetOrders() throws Exception {
        when(orderService.getOrders()).thenReturn(testScenarios.mockOrders());
        ResponseEntity responseEntity = orderController.getOrders();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testGetOrderByIdentifier() throws Exception {
        when(orderService.getOrderByIdentifier(1L)).thenReturn(testScenarios.mockOrder());
        ResponseEntity responseEntity = orderController.getOrderById(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteOrder() throws Exception {
        OrderService spy = spy(orderService);
        doNothing().when(spy).delete(1L);
        ResponseEntity responseEntity = orderController.deleteOrder(1L);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void testAddOrder() throws Exception {
        Order order = testScenarios.mockOrder();
        order.setIdentifier(1L);
        order.setId("1");
        when(orderService.save(any(Order.class))).thenReturn(order);
        ResponseEntity responseEntity = orderController.addOrder(OrderDTOMapper.fromOrder(order));
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateOrder() throws Exception {
        Order order = testScenarios.mockOrder();
        order.setIdentifier(1L);
        order.setId("1");
        when(orderService.save(any(Order.class))).thenReturn(order);
        ResponseEntity responseEntity = orderController.updateOrder(OrderDTOMapper.fromOrder(order));
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

}
