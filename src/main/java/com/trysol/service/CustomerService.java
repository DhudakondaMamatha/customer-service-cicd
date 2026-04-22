package com.trysol.service;

import com.trysol.dto.request.Customer;
import com.trysol.entity.CustomerEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Page<CustomerEntity> getCustomers(int pageNumber);
    Customer getCustomerById(Integer id);
    Customer getCustomerByEmail(String email);
    Customer updateCustomer(Customer customer);
    Customer patchCustomer(Map<String,Object> data,Integer id);
}
