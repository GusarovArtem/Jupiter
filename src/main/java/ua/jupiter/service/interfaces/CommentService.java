package ua.jupiter.service.interfaces;

import org.springframework.stereotype.Service;
import ua.jupiter.dto.create.CommentCreateEditDto;
import ua.jupiter.dto.read.CommentReadDto;

@Service
public interface CommentService {
    CommentReadDto createComment(CommentCreateEditDto comment);

    boolean deleteComment(Long commentId);
}
