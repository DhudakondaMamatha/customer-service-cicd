package com.trysol.controller;

import com.trysol.dto.request.Customer;
import com.trysol.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit Tests for CustomerController
 * Uses Mockito to mock service - no database needed!
 */
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        testCustomer = Customer.builder()
                .id(1)
                .name("John Doe")
                .email("john@example.com")
                .build();
    }

    @Test
    @DisplayName("POST /customers - Create customer should return saved customer")
    void testCreateCustomer_ValidData_ReturnsSavedCustomer() {
        // ARRANGE
        when(customerService.createCustomer(any(Customer.class))).thenReturn(testCustomer);

        // ACT
        Customer result = customerController.createCustomer(testCustomer);

        // ASSERT
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("John Doe", result.getName());
        verify(customerService, times(1)).createCustomer(any(Customer.class));
    }

    @Test
    @DisplayName("GET /customers/id/{id} - Should return customer by ID")
    void testGetCustomerById_ExistingId_ReturnsCustomer() {
        // ARRANGE
        when(customerService.getCustomerById(1)).thenReturn(testCustomer);

        // ACT
        Customer result = customerController.getCustomerById(1);

        // ASSERT
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("John Doe", result.getName());
        verify(customerService, times(1)).getCustomerById(1);
    }
}