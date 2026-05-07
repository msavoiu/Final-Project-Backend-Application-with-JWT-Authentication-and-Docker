package com.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entities.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    public List<Brand> findAllByOwnerEmail(String ownerEmail);
}
