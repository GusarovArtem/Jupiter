package ua.jupiter.service.interfaces;

import ua.jupiter.dto.create.CommentCreateEditDto;
import ua.jupiter.dto.read.CommentReadDto;

public interface CommentService {
    CommentReadDto createComment(CommentCreateEditDto comment);

    boolean deleteMessage(Long commentId);
}
