package ua.jupiter.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import ua.jupiter.database.entity.Message;
import ua.jupiter.database.entity.View;
import ua.jupiter.database.repository.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("message")
public class MessageController {

    private final MessageRepository messageRepository;

    @GetMapping()
    @JsonView(View.IdName.class)
    public List<Message> list() {
        return messageRepository.findAll();
    }

    @GetMapping("{id}")
    @JsonView(View.FullMessage.class)
    public Message getOne(@PathVariable("id") Message message) {
        return message;
    }

    @PostMapping
    public Message create(@RequestBody Message message) {
        message.setCreationDate(LocalDateTime.now());
        return messageRepository.save(message);
    }

    @PutMapping("{id}")
    public Message update(
            @PathVariable("id") Message messageFromDb,
            @RequestBody Message message) {
        BeanUtils.copyProperties(message, messageFromDb, "id");
        return messageRepository.save(messageFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {

        messageRepository.delete(message);
    }
}
