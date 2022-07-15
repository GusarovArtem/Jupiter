package ua.jupiter.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ua.jupiter.database.entity.View;
import ua.jupiter.database.entity.message.Message;
import ua.jupiter.database.entity.user.User;
import ua.jupiter.dto.create.MessageCreateEditDto;
import ua.jupiter.dto.read.MessageReadDto;
import ua.jupiter.dto.read.UserReadDto;
import ua.jupiter.service.implementation.MessageServiceImpl;

import java.util.List;

@RestController
@RequestMapping("message")
@RequiredArgsConstructor
public class MessageController {

    public static final int MESSAGES_PER_PAGE = 3;

    private final MessageServiceImpl messageService;

    @GetMapping
    @JsonView(View.FullMessage.class)
    public List<MessageReadDto> list(
            @AuthenticationPrincipal User user,
            Pageable pageable
    ) {

        return messageService.findAllByAuthor(user, pageable);
    }

    @GetMapping("{id}")
    @JsonView(View.FullMessage.class)
    public Message getOne(@PathVariable("id") Message message) {
        return message;
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
    public MessageReadDto updateMessage(
            @PathVariable("id") Message messageFromDb,
            @RequestBody MessageReadDto message
    ) {
        return messageService.updateMessage(messageFromDb.getId(), message).get();
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable("id") Long id
    ) {
        if (!messageService.deleteMessage(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}