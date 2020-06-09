package br.com.zup.validator;

import br.com.zup.entity.Order;
import br.com.zup.entity.Product;
import br.com.zup.exception.ApiErrorException;
import br.com.zup.mock.TestScenarios;
import br.com.zup.validator.order.OrderValidator;
import br.com.zup.validator.order.impl.OrderValidatorImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class OrderValidatorTests {
    
    private OrderValidator orderValidator = new OrderValidatorImpl();
    private TestScenarios testScenarios = new TestScenarios();

    @Test
    public void testValidate() {
        Order order = testScenarios.mockOrder();
        order.getProducts().get(0).setIdentifier(null);
        assertMessage(order, "Product identifier cannot be null");
        order.setProducts(null);
        assertMessage(order, "Products cannot be empty");
        order.setCustomerName(null);
        assertMessage(order, "Customer name cannot be empty");

    }

    private void assertMessage(Order order, String message) {
        try {
            orderValidator.validate(order);
        } catch (ApiErrorException e) {
            assertEquals(message, e.getMessage());
        }
    }
}
