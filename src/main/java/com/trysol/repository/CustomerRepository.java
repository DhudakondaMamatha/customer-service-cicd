package com.trysol.repository;

import com.trysol.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity,Integer> {
   Optional<CustomerEntity> findByEmail(String email);
}
