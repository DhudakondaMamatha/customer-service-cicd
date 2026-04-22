package com.trysol.service.impl;

import com.trysol.dto.request.Customer;
import com.trysol.entity.CustomerEntity;
import com.trysol.handler.exception.CustomerException;
import com.trysol.repository.CustomerRepository;
import com.trysol.service.CustomerService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {

        CustomerEntity customerEntity = CustomerEntity.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .build();
        this.customerRepository.save(customerEntity);

        return Customer.builder()
                .id(customerEntity.getId())
                .name(customerEntity.getName())
                .email(customer.getEmail())
                .build();

    }

    @Override
    public Page<CustomerEntity> getCustomers(int pageNumber) {
        int pageSize =1;
        Pageable  pageable= PageRequest.of(pageNumber,pageSize);
        return customerRepository.findAll(pageable);
    }

    @Override
    public Customer getCustomerById(Integer id) {
        com.trysol.entity.CustomerEntity entity = customerRepository
                .findById(id)
                .orElseThrow(() -> new
                        CustomerException("Customer not found in our records with customer id :" + id));
        return Customer.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .build();
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        com.trysol.entity.CustomerEntity entity = customerRepository
                .findByEmail(email)
                .orElseThrow(() -> new
                        CustomerException("Customer not found in our records with customer email :" + email));
        return Customer.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .build();
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        CustomerEntity customerEntity= CustomerEntity.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .build();
        this.customerRepository.save(customerEntity);
        return customer;
    }


    @Override
    public Customer patchCustomer(Map<String, Object> data, Integer id) {
            CustomerEntity customerEntity = this.customerRepository.findById(id).get();

            if (customerEntity != null) {
                data.forEach((key, value) -> {
                    Field field = ReflectionUtils.findField(CustomerEntity.class, key);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, customerEntity, value);
                });
                this.customerRepository.save(customerEntity);
            }
        return null;
    }

}
