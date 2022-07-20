package ua.jupiter.http.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ua.jupiter.http.dto.create.CommentCreateEditDto;
import ua.jupiter.http.dto.read.message.CommentReadDto;
import ua.jupiter.database.entity.user.OAuth2User;
import ua.jupiter.service.implementation.message.CommentServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CommentController {

    CommentServiceImpl commentService;

    @PostMapping
    public CommentReadDto createComment(
            @RequestBody CommentCreateEditDto comment,
            @AuthenticationPrincipal OAuth2User oauthUser
    ) {
        comment.setAuthorId(oauthUser.getName());

        return commentService.createComment(comment);
    }

    @DeleteMapping("{comment_id}")
    public void deleteMessage(@PathVariable("comment_id") Long commentId) {
        if (!commentService.deleteComment(commentId)) {
            new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}