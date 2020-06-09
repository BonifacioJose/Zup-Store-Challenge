package br.com.zup.validator.order;

import br.com.zup.entity.Order;
import br.com.zup.exception.ApiErrorException;

public interface OrderValidator {

    public void validate(Order order) throws ApiErrorException;

}
