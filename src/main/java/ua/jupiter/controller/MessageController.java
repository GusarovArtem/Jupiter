package ua.jupiter.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ua.jupiter.database.entity.View;
import ua.jupiter.database.entity.message.Message;
import ua.jupiter.database.entity.user.User;
import ua.jupiter.dto.MessagePageDto;
import ua.jupiter.dto.create.MessageCreateEditDto;
import ua.jupiter.dto.read.MessageReadDto;
import ua.jupiter.dto.read.UserReadDto;
import ua.jupiter.service.implementation.MessageServiceImpl;

import java.io.IOException;

@RestController
@RequestMapping("message")
@RequiredArgsConstructor
public class MessageController {
    public static final int MESSAGES_PER_PAGE = 3;

    private final MessageServiceImpl messageService;

    @GetMapping
    @JsonView(View.FullMessage.class)
    public MessagePageDto getAllMessagesByAuthor(
            @AuthenticationPrincipal User user,
            @PageableDefault(size = MESSAGES_PER_PAGE, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return messageService.getAllMessagesByAuthor(user, pageable);
    }

    @GetMapping("{id}")
    @JsonView(View.FullMessage.class)
    public Message getMessage(@PathVariable("id") Long messageId) {
        return messageService.getById(messageId);
    }

    @PostMapping
    @JsonView(View.FullMessage.class)
    public Message createMessage(
            @RequestBody MessageCreateEditDto message,
            @AuthenticationPrincipal UserReadDto user
    ) {
        message.setAuthor(user);

        return messageService.createMessage(message);
    }

    @PutMapping("{id}")
    @JsonView(View.FullMessage.class)
    public MessageReadDto update(
            @PathVariable("id") Long messageFromDbId,
            @RequestBody MessageReadDto message
    ) throws IOException {
        return messageService.updateMessage(messageFromDbId, message);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long messageId) {
        if (!messageService.deleteMessage(messageId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}