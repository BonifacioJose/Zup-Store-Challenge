package br.com.zup.api.v1.controller;

import br.com.zup.api.v1.mapper.ProductDTOMapper;
import br.com.zup.entity.Product;
import br.com.zup.mock.TestScenarios;
import br.com.zup.service.product.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class ProductControllerTests {

    private ProductController productController;

    @MockBean
    private ProductService productService;

    private TestScenarios testScenarios = new TestScenarios();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        productController = new ProductController(productService);
    }

    @Test
    public void testGetProducts() throws Exception {
        when(productService.getProducts()).thenReturn(testScenarios.mockProducts());
        ResponseEntity responseEntity = productController.getProducts();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testGetProductByIdentifier() throws Exception {
        when(productService.getProductByIdentifier(1L)).thenReturn(testScenarios.mockProduct());
        ResponseEntity responseEntity = productController.getProductById(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteProduct() throws Exception {
        ProductService spy = spy(productService);
        doNothing().when(spy).delete(1L);
        ResponseEntity responseEntity = productController.deleteProduct(1L);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void testAddProduct() throws Exception {
        Product product = testScenarios.mockProduct();
        product.setIdentifier(1L);
        product.setId("1");
        when(productService.save(any(Product.class))).thenReturn(product);
        ResponseEntity responseEntity = productController.addProduct(ProductDTOMapper.fromProduct(product));
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateProduct() throws Exception {
        Product product = testScenarios.mockProduct();
        product.setIdentifier(1L);
        product.setId("1");
        when(productService.save(any(Product.class))).thenReturn(product);
        ResponseEntity responseEntity = productController.updateProduct(ProductDTOMapper.fromProduct(product));
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

}
