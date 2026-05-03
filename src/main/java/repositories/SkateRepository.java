package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.Skate;

public interface SkateRepository extends JpaRepository<Skate, Long> {

    public List<Skate> findAllByOwnerEmail(String ownerEmail);
}
