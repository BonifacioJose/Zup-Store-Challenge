package br.com.zup.service.order.impl;

import br.com.zup.entity.Order;
import br.com.zup.entity.Product;
import br.com.zup.exception.ApiErrorException;
import br.com.zup.repository.OrderRepository;
import br.com.zup.service.order.OrderService;
import br.com.zup.service.product.ProductService;
import br.com.zup.validator.order.OrderValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);

    private ProductService productService;
    private OrderRepository orderRepository;
    private OrderValidator orderValidator;

    public OrderServiceImpl(@Autowired OrderRepository orderRepository, @Autowired ProductService productService,
                            @Autowired OrderValidator orderValidator) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.orderValidator = orderValidator;
    }

    @Override
    public Order getOrderByIdentifier(Long identifier) throws ApiErrorException {
        logger.info(String.format("Get product by identifier %s", identifier));
        Optional<Order> order = orderRepository.findFirstByIdentifier(identifier);
        if (order.isPresent()) {
            logger.info(String.format("Found product by identifier %s", identifier));
            return order.get();
        }
        logger.error(String.format("Get product by identifier %s", identifier));
        throw new ApiErrorException(HttpStatus.NOT_FOUND, "Order not found");
    }

    @Override
    public List<Order> getOrders() {
        logger.info("Get Orders");
        return orderRepository.findAll();
    }

    @Override
    public Order save(Order order) throws ApiErrorException {
        try {
            logger.info(String.format("Validate order by customer %s", order.getCustomerName()));
            orderValidator.validate(order);
            if (order.getIdentifier() == null) {
                logger.info(String.format("Create order by customer %s", order.getCustomerName()));
                Optional<Order> optionalOrder = orderRepository.findFirstByOrderByIdentifierDesc();
                order.setIdentifier(optionalOrder.isPresent() && optionalOrder.get().getIdentifier() != null ? optionalOrder.get().getIdentifier() + 1L : 1L);
            } else {
                logger.info(String.format("Update order by customer %s", order.getCustomerName()));
                Optional<Order> optionalOrder = orderRepository.findFirstByIdentifier(order.getIdentifier());
                order.setId(optionalOrder.map(Order::getId).orElse(null));
            }

            logger.info(String.format("Fetch products for order %s by customer %s", order.getIdentifier(), order.getCustomerName()));
            List<Product> productsDatabase = new ArrayList<>();
            for (Product product : order.getProducts()) {
                productsDatabase.add(productService.getProductByIdentifier(product.getIdentifier()));
            }
            order.setProducts(productsDatabase);
            order.setProductsTotal(order.getProducts().stream().map(Product::getPrice).reduce(BigDecimal::add).get());
            order.setTotal(order.getProductsTotal().subtract(order.getDiscount()));

            logger.info(String.format("Save order by customer %s", order.getCustomerName()));
            return orderRepository.save(order);
        } catch (Exception e) {
            logger.error(String.format("Error saving order by customer %s", order.getCustomerName()));
            if (e instanceof ApiErrorException) {
                throw e;
            }
            throw new ApiErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void delete(Long identifier) throws ApiErrorException {
        try {
            logger.info(String.format("Deleting order %s", identifier));
            Order order = getOrderByIdentifier(identifier);
            orderRepository.delete(order);
        } catch (Exception e) {
            logger.error(String.format("Error deleting order %s", identifier));
            if (e instanceof ApiErrorException) {
                throw e;
            }
            throw new ApiErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
