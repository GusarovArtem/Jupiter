package ua.jupiter.database.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.jupiter.database.entity.User;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<User, String> {
    @EntityGraph(attributePaths = { "subscriptions", "subscribers" })
    Optional<User> findById(String s);
}