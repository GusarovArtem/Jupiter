package ua.jupiter.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
