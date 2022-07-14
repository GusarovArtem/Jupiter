package ua.jupiter.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.jupiter.dto.read.CommentReadDto;

public interface CommentRepository extends JpaRepository<CommentReadDto, Long> {
}
