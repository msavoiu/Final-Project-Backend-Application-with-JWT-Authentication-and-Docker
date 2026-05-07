package com.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entities.Blade;

public interface BladeRepository extends JpaRepository<Blade, Long> {

    public List<Blade> findAllByOwnerEmail(String ownerEmail);
}
