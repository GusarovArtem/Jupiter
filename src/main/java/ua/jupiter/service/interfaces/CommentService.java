package ua.jupiter.service.interfaces;

import org.springframework.stereotype.Service;
import ua.jupiter.api.dto.create.CommentCreateEditDto;
import ua.jupiter.api.dto.read.message.CommentReadDto;

@Service
public interface CommentService {
    CommentReadDto createComment(CommentCreateEditDto comment);

    boolean deleteComment(Long commentId);
}