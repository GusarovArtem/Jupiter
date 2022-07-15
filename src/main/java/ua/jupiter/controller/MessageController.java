package ua.jupiter.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua.jupiter.database.entity.View;
import ua.jupiter.database.entity.message.Message;
import ua.jupiter.database.entity.user.User;
import ua.jupiter.dto.MessagePageDto;
import ua.jupiter.dto.create.MessageCreateEditDto;
import ua.jupiter.dto.read.MessageReadDto;
import ua.jupiter.service.implementation.MessageServiceImpl;

@RestController
@RequestMapping("message")
@RequiredArgsConstructor
public class MessageController {

    public static final int MESSAGES_PER_PAGE = 3;

    private final MessageServiceImpl messageService;

    @GetMapping
    @JsonView(View.FullMessage.class)
    public MessagePageDto list(
            @AuthenticationPrincipal User user,
            @PageableDefault(size = MESSAGES_PER_PAGE, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(0, MessageController.MESSAGES_PER_PAGE, sort);

        return messageService.findAllUserMessages(pageRequest, user.getId());
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
            @AuthenticationPrincipal User user
    ) {
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

    @DeleteMapping("{id}")
    public void deleteMessage(@PathVariable("id") Message message) {
        messageService.deleteMessage(message.getId());
    }

}