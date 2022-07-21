package ua.jupiter.service.decorator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.jupiter.api.dto.create.CommentCreateEditDto;
import ua.jupiter.api.dto.read.message.CommentReadDto;
import ua.jupiter.service.implementation.message.CommentServiceImpl;
import ua.jupiter.service.interfaces.CommentService;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LoggedCommentService implements CommentService {

    CommentServiceImpl commentServiceImpl;

    @Override
    public CommentReadDto createComment(CommentCreateEditDto comment) {

        log.info("Create comment: ");
        log.info("User id: " + comment.getAuthorId());
        log.info("Comment text: " + comment.getText());
        log.info("Message id: " + comment.getMessageId());

        return commentServiceImpl.createComment(comment);
    }

    @Override
    public boolean deleteComment(Long commentId) {

        log.info("Comment id for delete:" + commentId);

        return commentServiceImpl.deleteComment(commentId);
    }
}