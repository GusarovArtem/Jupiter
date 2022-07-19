package ua.jupiter.http.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ua.jupiter.database.entity.user.OAuth2User;
import ua.jupiter.database.entity.View;
import ua.jupiter.database.entity.message.Message;
import ua.jupiter.database.entity.user.User;
import ua.jupiter.service.implementation.message.MessageServiceImpl;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/messages")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MessageController {

    MessageServiceImpl messageService;


    @GetMapping
    @JsonView(View.FullMessage.class)
    public List<Message> findForUser(
            @AuthenticationPrincipal OAuth2User oauthUser) {

        return messageService.findForUser(oauthUser.getUser());
    }

    @GetMapping("{message_id}")
    @JsonView(View.FullMessage.class)
    public Message getOne(
            @PathVariable(name = "message_id") Message message) {

        return message;
    }


    @PostMapping
    @JsonView(View.FullMessage.class)
    public Message addMessage(
            @RequestBody Message message,
            @AuthenticationPrincipal OAuth2User oauthUser) {

        User user = oauthUser.getUser();

        return messageService.createMessage(message, user);
    }

    @PutMapping("{message_id}")
    @JsonView(View.FullMessage.class)
    public Message updateMessage(
            @PathVariable("message_id") Long messageId,
            @RequestBody Message message) {

        return messageService
                .updateMessage(messageId, message)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{message_id}")
    public void deleteMessage(@PathVariable("message_id") Long messageId) {
        if (!messageService.deleteMessage(messageId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}