package ua.jupiter.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;
import ua.jupiter.database.entity.message.Message;
import ua.jupiter.database.entity.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends
        JpaRepository<Message, Long>,
        RevisionRepository<Message, Long, Long> {

    @Override
//    @EntityGraph("withCommentsAndAuthor")
    @Query("SELECT m FROM Message m " +
            "LEFT JOIN FETCH m.comments c " +
            "JOIN FETCH m.author u " +
            "LEFT JOIN u.subscribers " +
            "LEFT JOIN u.subscriptions " +
            "WHERE m.id = :messageId")
    Optional<Message> findById(Long messageId);

    List<Message> findAllByTextContainingIgnoreCase(String prefixName);

    @EntityGraph(attributePaths = { "comments" })
    List<Message> findByAuthorIn(List<User> userEntity, Sort sort);

}