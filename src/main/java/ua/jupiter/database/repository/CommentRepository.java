package ua.jupiter.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.jupiter.database.entity.message.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
