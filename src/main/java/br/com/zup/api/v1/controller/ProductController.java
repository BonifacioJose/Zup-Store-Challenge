package br.com.zup.api.v1.controller;

import br.com.zup.api.v1.V1Controller;
import br.com.zup.api.v1.dto.ProductDTO;
import br.com.zup.api.v1.mapper.ProductDTOMapper;
import br.com.zup.exception.ApiErrorException;
import br.com.zup.service.product.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class ProductController extends V1Controller {

    private static final Logger logger = Logger.getLogger(ProductController.class);

    private ProductService productService;

    public ProductController(@Autowired ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    public ResponseEntity addProduct(@RequestBody ProductDTO product) throws ApiErrorException, URISyntaxException {
        logger.info(String.format("Add Product %s", product.getName()));
        return ResponseEntity.created(new URI("/product/" + productService.save(ProductDTOMapper.toProduct(product))
                .getIdentifier())).build();
    }

    @PutMapping("/product")
    public ResponseEntity updateProduct(@RequestBody ProductDTO product) throws ApiErrorException {
        logger.info(String.format("Update Product %s", product.getName()));
        productService.save(ProductDTOMapper.toProduct(product));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity getProductById(@PathVariable("productId") Long identifier) throws ApiErrorException {
        logger.info(String.format("Get Product %s", identifier));
        return ResponseEntity.ok(ProductDTOMapper.fromProduct(productService.getProductByIdentifier(identifier)));
    }

    @GetMapping("/products")
    public ResponseEntity getProducts() {
        logger.info("Get Products");
        return ResponseEntity.ok(productService.getProducts().stream().map(ProductDTOMapper::fromProduct));
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity deleteProduct(@PathVariable("productId") Long identifier) throws ApiErrorException {
        logger.info(String.format("Delete Product %s", identifier));
        productService.delete(identifier);
        return ResponseEntity.noContent().build();
    }

}
