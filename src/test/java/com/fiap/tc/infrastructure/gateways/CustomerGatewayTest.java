package com.fiap.tc.infrastructure.gateways;

import br.com.six2six.fixturefactory.Fixture;
import com.fiap.tc.domain.entities.Customer;
import com.fiap.tc.domain.exceptions.NotFoundException;
import com.fiap.tc.fixture.FixtureTest;
import com.fiap.tc.infrastructure.services.CustomerServiceClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerGatewayTest extends FixtureTest {

    @Mock
    private CustomerServiceClient customerServiceClientMock;

    @InjectMocks
    private CustomerGateway customerGateway;

    private String document;
    private Customer customer;

    @BeforeEach
    public void setUp() {
        // Arrange
        document = "11111111111";
        customer = Fixture.from(Customer.class).gimme("valid");
    }

    @Test
    public void loadCustomerTest() {
        // Arrange
        when(customerServiceClientMock.load(document)).thenReturn(Optional.of(customer));

        // Act
        var customerResult = customerGateway.load(document);

        // Assertions
        assertEquals(customer, customerResult);
        verify(customerServiceClientMock, times(1)).load(document);

    }

    @Test
    public void loadCustomer_LaunchException_WhenCustomerNotFoundTest() {
        // Arrange
        when(customerServiceClientMock.load(document)).thenReturn(Optional.empty());

        // Act
        var assertThrows = Assertions.assertThrows(NotFoundException.class, () -> customerGateway.load(document));

        // Assertions
        verify(customerServiceClientMock, times(1)).load(document);
        Assertions.assertTrue(assertThrows.getMessage().contains("not found"));


    }

}