package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.Backorder;

public interface BackorderRepository extends JpaRepository<Backorder, Long> {

    public List<Backorder> findAllByOwnerEmail(String ownerEmail);
}
