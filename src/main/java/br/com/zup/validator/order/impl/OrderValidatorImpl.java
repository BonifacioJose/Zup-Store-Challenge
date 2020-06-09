package br.com.zup.validator.order.impl;

import br.com.zup.entity.Order;
import br.com.zup.entity.Product;
import br.com.zup.exception.ApiErrorException;
import br.com.zup.validator.order.OrderValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class OrderValidatorImpl implements OrderValidator {

    @Override
    public void validate(Order order) throws ApiErrorException {
        if (StringUtils.isEmpty(order.getCustomerName())) {
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, "Customer name cannot be empty");
        }
        if (CollectionUtils.isEmpty(order.getProducts())) {
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, "Products cannot be empty");
        }
        for (Product product : order.getProducts()) {
            if (product.getIdentifier() == null) {
                throw new ApiErrorException(HttpStatus.BAD_REQUEST, "Product identifier cannot be null");
            }
        }
    }

}
