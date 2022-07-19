package ua.jupiter.service.decorator;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.jupiter.database.entity.message.Message;
import ua.jupiter.database.entity.user.User;
import ua.jupiter.service.implementation.message.MessageServiceImpl;
import ua.jupiter.service.interfaces.MessageService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class LoggedMessageService implements MessageService {

    MessageServiceImpl messageServiceImpl;

    @Override
    public List<Message> findForUser(User User) {
        log.info("Messages for user:");
        log.info("User id: " + User.getId());
        log.info("User name: " + User.getName());

        return messageServiceImpl.findForUser(User);
    }

    @Override
    public Optional<Message> updateMessage(Long messageId, Message message) {
        log.info("Message id: " + messageId);
        log.info("Message new text: " + message.getText());

        return messageServiceImpl.updateMessage(messageId, message);

    }

    @Override
    public Optional<Message> getMessageById(Long messageId) {
        log.info("Message id: " + messageId);
        return messageServiceImpl.getMessageById(messageId);
    }

    @Override
    public boolean deleteMessage(Long messageId) {
        log.info("Message id for delete:" + messageId);

        return messageServiceImpl.deleteMessage(messageId);
    }

    @Override
    public Message createMessage(Message message, User user) {
        log.info("Create message: ");
        log.info("User id: " + user.getId());
        log.info("Message text: " + message.getText());

        return messageServiceImpl.createMessage(message, user);
    }

    @Override
    public List<Message> getListMessages(Optional<String> optionalPrefixName) {
        log.info("Search messages: " + optionalPrefixName.get());

        return messageServiceImpl.getListMessages(optionalPrefixName);
    }

    @Override
    public Optional<Message> findById(Long messageId) {
        log.info("Find Message by id: " + messageId);

        return messageServiceImpl.findById(messageId);
    }
}