package br.com.zup.service.order;

import br.com.zup.entity.Order;
import br.com.zup.exception.ApiErrorException;

import java.util.List;

public interface OrderService {

    public Order getOrderByIdentifier(Long identifier) throws ApiErrorException;
    public List<Order> getOrders();
    public Order save(Order product) throws ApiErrorException;
    public void delete(Long identifier) throws ApiErrorException;

}
