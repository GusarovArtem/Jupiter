package ua.jupiter.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ua.jupiter.database.entity.View;
import ua.jupiter.dto.create.CommentCreateEditDto;
import ua.jupiter.dto.read.CommentReadDto;
import ua.jupiter.service.implementation.CommentServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("comment")
public class CommentController {
    private final CommentServiceImpl commentService;

    @PostMapping
    @JsonView(View.FullComment.class)
    public CommentReadDto create(
            @RequestBody CommentCreateEditDto comment
    ) {
        return commentService.createComment(comment);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable("id") Long id
    ) {
        if (!commentService.deleteComment(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
