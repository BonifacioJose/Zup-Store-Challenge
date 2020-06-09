package br.com.zup.validator.product.impl;

import br.com.zup.entity.Product;
import br.com.zup.exception.ApiErrorException;
import br.com.zup.validator.product.ProductValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ProductValidatorImpl implements ProductValidator {

    @Override
    public void validate(Product product) throws ApiErrorException {
        if (StringUtils.isEmpty(product.getName())) {
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, "Name cannot be empty");
        }
        if (StringUtils.isEmpty(product.getManufacturer())) {
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, "Manufacturer cannot be empty");
        }
        if (StringUtils.isEmpty(product.getSku())) {
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, "Sku cannot be empty");
        }
        if (product.getPrice() == null) {
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, "Price cannot be null");
        }
    }

}
