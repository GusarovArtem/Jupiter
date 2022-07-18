package ua.jupiter.service.interfaces;

import org.springframework.stereotype.Service;
import ua.jupiter.api.dto.create.CommentCreateEditDto;
import ua.jupiter.api.dto.read.CommentReadDto;

@Service

public interface CommentService {
    CommentReadDto create(CommentCreateEditDto comment);

    boolean deleteComment(Long commentId);
}