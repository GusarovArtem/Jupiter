package ua.jupiter.service.interfaces;

import org.springframework.stereotype.Service;
import ua.jupiter.http.dto.create.CommentCreateEditDto;
import ua.jupiter.http.dto.read.message.CommentReadDto;

@Service
public interface CommentService {
    CommentReadDto createComment(CommentCreateEditDto comment);

    boolean deleteComment(Long commentId);
}