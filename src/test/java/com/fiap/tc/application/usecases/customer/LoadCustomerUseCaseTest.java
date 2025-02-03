package com.fiap.tc.application.usecases.customer;

import br.com.six2six.fixturefactory.Fixture;
import com.fiap.tc.application.gateways.CustomerGatewaySpec;
import com.fiap.tc.domain.entities.Customer;
import com.fiap.tc.fixture.FixtureTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoadCustomerUseCaseTest extends FixtureTest {

    @Mock
    private CustomerGatewaySpec customerGatewaySpecMock;

    @InjectMocks
    private LoadCustomerUseCase loadCustomerUseCase;

    private Customer customer;
    private static final String DOCUMENT = "11111111111";

    @BeforeEach
    public void setUp() {
        // Arrange
        customer = Fixture.from(Customer.class).gimme("valid");
        when(customerGatewaySpecMock.load(DOCUMENT)).thenReturn(customer);
    }

    @Test
    public void loadCustomerTest() {
        // Act
        var customerResult = loadCustomerUseCase.load(DOCUMENT);

        // Assertions
        assertEquals(customer, customerResult);
        verify(customerGatewaySpecMock, times(1)).load(DOCUMENT);

    }


}