package br.com.zup.service.product.impl;

import br.com.zup.entity.Product;
import br.com.zup.exception.ApiErrorException;
import br.com.zup.repository.ProductRepository;
import br.com.zup.service.product.ProductService;
import br.com.zup.validator.product.ProductValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);

    private ProductRepository productRepository;
    private ProductValidator productValidator;

    public ProductServiceImpl(@Autowired ProductRepository productRepository, @Autowired ProductValidator productValidator) {
        this.productRepository = productRepository;
        this.productValidator = productValidator;
    }

    @Override
    public Product getProductByIdentifier(Long identifier) throws ApiErrorException {
        logger.info(String.format("Get product by identifier %s", identifier));
        Optional<Product> product = productRepository.findFirstByIdentifier(identifier);
        if (product.isPresent()) {
            logger.info(String.format("Found product by identifier %s", identifier));
            return product.get();
        }

        logger.error(String.format("Could not find product by identifier %s", identifier));
        throw new ApiErrorException(HttpStatus.NOT_FOUND, "Product not found");
    }

    @Override
    public List<Product> getProducts() {
        logger.info("Get Products");
        return productRepository.findAll();
    }

    @Override
    public Product save(Product product) throws ApiErrorException {
        try {
            logger.info(String.format("Validate product %s", product.getName()));
            productValidator.validate(product);
            if (product.getIdentifier() == null) {
                logger.info(String.format("Create product %s", product.getName()));
                Optional<Product> optionalProduct = productRepository.findFirstByOrderByIdentifierDesc();
                product.setIdentifier(optionalProduct.isPresent() && optionalProduct.get().getIdentifier() != null ? optionalProduct.get().getIdentifier() + 1L : 1L);
            } else {
                logger.info(String.format("Update product %s", product.getName()));
                Optional<Product> optionalProduct = productRepository.findFirstByIdentifier(product.getIdentifier());
                product.setId(optionalProduct.map(Product::getId).orElse(null));
            }
            logger.info(String.format("Save product %s", product.getName()));
            return productRepository.save(product);
        } catch (Exception e) {
            logger.error(String.format("Error saving product %s. Cause: %s", product.getName(), e.getMessage()));
            if (e instanceof ApiErrorException) {
                throw e;
            }
            throw new ApiErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void delete(Long identifier) throws ApiErrorException {
        try {
            logger.info(String.format("Delete product %s", identifier));
            Product product = getProductByIdentifier(identifier);
            productRepository.delete(product);
        } catch (Exception e) {
            logger.error(String.format("Error deleting product %s. Cause: %s", identifier, e.getMessage()));
            if (e instanceof ApiErrorException) {
                throw e;
            }
            throw new ApiErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
