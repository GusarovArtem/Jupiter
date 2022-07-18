package ua.jupiter.database.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.jupiter.database.entity.user.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @EntityGraph(attributePaths = { "subscriptions", "subscribers" })
    Optional<User> findById(String id);

    @EntityGraph(attributePaths = { "subscriptions", "subscribers" })
    User getById(String id);

    @Query("select u from User u " +
            "left join fetch  u.subscriptions  " +
            "left join fetch u.subscribers  " +
            "where u.id = :id")
    User getProfile(String id);
}