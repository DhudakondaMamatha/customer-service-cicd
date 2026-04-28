package com.trysol.service;

import com.trysol.dto.request.Customer;
import com.trysol.entity.CustomerEntity;
import com.trysol.repository.CustomerRepository;
import com.trysol.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit Tests for CustomerService
 * Uses Mockito to mock repository - no database needed!
 */
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer testCustomer;
    private CustomerEntity testEntity;

    @BeforeEach
    void setUp() {
        testCustomer = Customer.builder()
                .name("John Doe")
                .email("john@example.com")
                .build();

        testEntity = CustomerEntity.builder()
                .id(1)
                .name("John Doe")
                .email("john@example.com")
                .build();
    }

    @Test
    @DisplayName("Create customer with valid data should call repository save")
    void testCreateCustomer_ValidData_ReturnsSavedCustomer() {
        // ARRANGE
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(testEntity);

        // ACT
        Customer result = customerService.createCustomer(testCustomer);

        // ASSERT
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
        verify(customerRepository, times(1)).save(any(CustomerEntity.class));
    }

    @Test
    @DisplayName("Get customer by valid ID should return customer")
    void testGetCustomerById_ExistingId_ReturnsCustomer() {
        // ARRANGE
        when(customerRepository.findById(1)).thenReturn(Optional.of(testEntity));

        // ACT
        Customer result = customerService.getCustomerById(1);

        // ASSERT
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("John Doe", result.getName());
        verify(customerRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Get customer by non-existent ID should throw exception")
    void testGetCustomerById_NonExistentId_ThrowsException() {
        // ARRANGE
        when(customerRepository.findById(999)).thenReturn(Optional.empty());

        // ACT & ASSERT
        Exception exception = assertThrows(Exception.class, () -> {
            customerService.getCustomerById(999);
        });

        assertTrue(exception.getMessage().contains("999"));
    }
}