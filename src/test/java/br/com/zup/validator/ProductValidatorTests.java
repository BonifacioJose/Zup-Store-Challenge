package br.com.zup.validator;

import br.com.zup.entity.Product;
import br.com.zup.exception.ApiErrorException;
import br.com.zup.mock.TestScenarios;
import br.com.zup.validator.product.ProductValidator;
import br.com.zup.validator.product.impl.ProductValidatorImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class ProductValidatorTests {

    private ProductValidator productValidator = new ProductValidatorImpl();
    private TestScenarios testScenarios = new TestScenarios();

    @Test
    public void testValidate() {
        Product mockProduct = testScenarios.mockProduct();
        mockProduct.setPrice(null);
        assertMessage(mockProduct, "Price cannot be null");
        mockProduct.setSku(null);
        assertMessage(mockProduct, "Sku cannot be empty");
        mockProduct.setManufacturer(null);
        assertMessage(mockProduct, "Manufacturer cannot be empty");
        mockProduct.setName(null);
        assertMessage(mockProduct, "Name cannot be empty");

    }

    private void assertMessage(Product product, String message) {
        try {
            productValidator.validate(product);
        } catch (ApiErrorException e) {
            assertEquals(message, e.getMessage());
        }
    }
}
