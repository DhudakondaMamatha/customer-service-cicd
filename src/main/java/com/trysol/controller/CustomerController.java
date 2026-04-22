package com.trysol.controller;

import com.trysol.dto.request.Customer;
import com.trysol.entity.CustomerEntity;
import com.trysol.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer){
        return customerService.createCustomer(customer);
    }

    @GetMapping("id/{id}")
    public Customer getCustomerById(@PathVariable Integer id){
        return this.customerService.getCustomerById(id);
    }

    @GetMapping("email")
    public Customer getCustomerByEmail(@RequestParam String email){
        return this.customerService.getCustomerByEmail(email);
    }

    @GetMapping("{id}")
    public Page<CustomerEntity> getCustomers(@PathVariable("id") int pageNumber){
        return this.customerService.getCustomers(pageNumber);
    }

    @PutMapping
    public Customer updateCustomer(@RequestBody Customer customer){
        return this.customerService.updateCustomer(customer);
    }

    @PatchMapping("/{id}")
    public Customer patchCustomer(@RequestBody Map<String,Object> data,@PathVariable Integer id){
        return this.customerService.patchCustomer(data,id);
    }

}
