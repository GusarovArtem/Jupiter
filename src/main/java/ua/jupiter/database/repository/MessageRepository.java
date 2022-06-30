package ua.jupiter.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.jupiter.database.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
