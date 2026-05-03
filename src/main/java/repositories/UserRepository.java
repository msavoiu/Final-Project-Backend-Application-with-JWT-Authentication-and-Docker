package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public List<User> findAllByOwnerEmail(String ownerEmail);
}
