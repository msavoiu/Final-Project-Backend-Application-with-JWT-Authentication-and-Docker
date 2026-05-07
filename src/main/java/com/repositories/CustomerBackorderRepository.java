package com.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entities.CustomerBackorder;

public interface CustomerBackorderRepository extends JpaRepository<CustomerBackorder, Long> {

    public List<CustomerBackorder> findAllByOwnerEmail(String ownerEmail);
}
