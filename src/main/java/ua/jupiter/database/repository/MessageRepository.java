package ua.jupiter.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.jupiter.database.entity.message.Message;
import ua.jupiter.database.entity.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Override
    @Query("SELECT m FROM Message m LEFT JOIN FETCH m.comments c WHERE m.id = :messageId")
    Optional<Message> findById(Long messageId);

    @EntityGraph(attributePaths = {"comments"})
    List<Message> findAllByTextContainingIgnoreCase(String prefixName);

    @EntityGraph(attributePaths = { "comments" })
    Page<User> findByAuthorIdIn(List<String> users, Pageable pageable);

    @EntityGraph(attributePaths = { "messages" })
    Page<Message> findAllByAuthor(User author, Pageable pageable);
}
