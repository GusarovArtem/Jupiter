package ua.jupiter.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;
import ua.jupiter.database.entity.message.Comment;

@Repository
public interface CommentRepository extends
        JpaRepository<Comment, Long>,
        RevisionRepository<Comment, Long, Long> {
}