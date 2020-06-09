package br.com.zup.service.product;

import br.com.zup.entity.Product;
import br.com.zup.exception.ApiErrorException;

import java.util.List;

public interface ProductService {

    public Product getProductByIdentifier(Long identifier) throws ApiErrorException;
    public List<Product> getProducts();
    public Product save(Product product) throws ApiErrorException;
    public void delete(Long identifier) throws ApiErrorException;

}
