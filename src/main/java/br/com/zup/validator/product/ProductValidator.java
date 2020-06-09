package br.com.zup.validator.product;

import br.com.zup.entity.Product;
import br.com.zup.exception.ApiErrorException;

public interface ProductValidator {

    public void validate(Product product) throws ApiErrorException;

}
