package ua.jupiter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.jupiter.database.entity.Comment;
import ua.jupiter.database.entity.User;
import ua.jupiter.database.repository.CommentRepository;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment create(Comment comment, User user) {
        comment.setAuthor(user);
        commentRepository.save(comment);

        return comment;
    }
}
