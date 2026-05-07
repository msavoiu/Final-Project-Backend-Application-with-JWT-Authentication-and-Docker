package com.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entities.CustomerOrder;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {

    public List<CustomerOrder> findAllByOwnerEmail(String ownerEmail);
}
