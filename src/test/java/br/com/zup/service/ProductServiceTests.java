package br.com.zup.service;

import br.com.zup.entity.Product;
import br.com.zup.exception.ApiErrorException;
import br.com.zup.mock.TestScenarios;
import br.com.zup.repository.ProductRepository;
import br.com.zup.service.product.ProductService;
import br.com.zup.service.product.impl.ProductServiceImpl;
import br.com.zup.validator.product.ProductValidator;
import br.com.zup.validator.product.impl.ProductValidatorImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class ProductServiceTests {

    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;
    private ProductValidator productValidator;
    private TestScenarios testScenarios = new TestScenarios();

    @Before
    public void init() {
        productValidator = new ProductValidatorImpl();
        productService = new ProductServiceImpl(productRepository, productValidator);
    }

    @Test
    public void testCreate() throws ApiErrorException {
        Product databaseProduct = testScenarios.mockProduct();
        Product newProduct = testScenarios.mockProduct();
        databaseProduct.setIdentifier(1L);
        databaseProduct.setId("1");
        when(productRepository.findFirstByOrderByIdentifierDesc()).thenReturn(Optional.of(databaseProduct));
        when(productRepository.save(any(Product.class))).thenReturn(newProduct);
        newProduct = productService.save(newProduct);
        assertEquals(2L, newProduct.getIdentifier().longValue());
    }

    @Test
    public void testUpdate() throws ApiErrorException {
        Product databaseProduct = testScenarios.mockProduct();
        Product saveProduct = testScenarios.mockProduct();
        databaseProduct.setIdentifier(1L);
        databaseProduct.setId("1");
        when(productRepository.findFirstByIdentifier(1L)).thenReturn(Optional.of(databaseProduct));
        when(productRepository.save(any(Product.class))).thenReturn(saveProduct);
        databaseProduct = productService.save(saveProduct);
        assertEquals(databaseProduct.getId(), saveProduct.getId());
    }

    @Test
    public void testGetByIdentifier() throws ApiErrorException {
        Product databaseProduct = testScenarios.mockProduct();
        databaseProduct.setIdentifier(1L);
        databaseProduct.setId("1");
        when(productRepository.findFirstByIdentifier(1L)).thenReturn(Optional.of(databaseProduct));
        Product returnProduct = productService.getProductByIdentifier(1L);
        assertEquals(databaseProduct.getId(), returnProduct.getId());
    }

    @Test(expected = ApiErrorException.class)
    public void testGetByIdentifierException() throws ApiErrorException {
        Product databaseProduct = testScenarios.mockProduct();
        databaseProduct.setIdentifier(1L);
        databaseProduct.setId("1");
        productService.getProductByIdentifier(1L);
    }

    @Test
    public void testDeleteProduct() throws ApiErrorException {
        Product databaseProduct = testScenarios.mockProduct();
        databaseProduct.setIdentifier(1L);
        databaseProduct.setId("1");
        when(productRepository.findFirstByIdentifier(1L)).thenReturn(Optional.of(databaseProduct));
        productService.delete(1L);
        verify(productRepository, times(1)).delete(databaseProduct);
    }

    @Test(expected = ApiErrorException.class)
    public void testDeleteException() throws ApiErrorException {
        Product databaseProduct = testScenarios.mockProduct();
        databaseProduct.setIdentifier(1L);
        databaseProduct.setId("1");
        productService.delete(1L);
    }

    @Test
    public void testGetProducts() {
        List<Product> databaseProducts = testScenarios.mockProducts();

        when(productRepository.findAll()).thenReturn(databaseProducts);

        List<Product> products = productService.getProducts();
        assertEquals(databaseProducts.size(), products.size());
    }

}
