package br.com.zup.api.v1.controller;

import br.com.zup.api.v1.V1Controller;
import br.com.zup.api.v1.dto.OrderDTO;
import br.com.zup.api.v1.mapper.OrderDTOMapper;
import br.com.zup.exception.ApiErrorException;
import br.com.zup.service.order.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class OrderController extends V1Controller {

    private static final Logger logger = Logger.getLogger(OrderController.class);
    
    private OrderService orderService;

    public OrderController(@Autowired OrderService orderService) {
        this.orderService = orderService;
    }
    
    @PostMapping("/order")
    public ResponseEntity addOrder(@RequestBody OrderDTO order) throws ApiErrorException, URISyntaxException {
        logger.info(String.format("Add Order by %s", order.getCustomerName()));
        return ResponseEntity.created(new URI("/order/" + orderService.save(OrderDTOMapper.toOrder(order)).getIdentifier())).build();
    }

    @PutMapping("/order")
    public ResponseEntity updateOrder(@RequestBody OrderDTO order) throws ApiErrorException {
        logger.info(String.format("Update Order by %s", order.getCustomerName()));
        orderService.save(OrderDTOMapper.toOrder(order));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity getOrderById(@PathVariable("orderId") Long identifier) throws ApiErrorException {
        logger.info(String.format("Get Order %s", identifier));
        return ResponseEntity.ok(OrderDTOMapper.fromOrder(orderService.getOrderByIdentifier(identifier)));
    }

    @GetMapping("/orders")
    public ResponseEntity getOrders() {
        logger.info("Get Orders");
        return ResponseEntity.ok(orderService.getOrders().stream().map(OrderDTOMapper::fromOrder));
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity deleteOrder(@PathVariable("orderId") Long identifier) throws ApiErrorException {
        logger.info(String.format("Delete Order %s", identifier));
        orderService.delete(identifier);
        return ResponseEntity.noContent().build();
    }

}
