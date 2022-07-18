package ua.jupiter.service.interfaces;

import org.springframework.stereotype.Service;
import ua.jupiter.database.entity.message.Message;
import ua.jupiter.database.entity.user.User;

import java.util.List;
import java.util.Optional;

@Service
public interface MessageService {
    List<Message> findForUser(User userEntity);

    Optional<Message> updateMessage(Long messageId, Message message);

    Optional<Message> getMessageById(Long messageId);

    boolean deleteMessage(Long messageId);

    Message createMessage(Message message, User user);

    List<Message> getListMessages(Optional<String> optionalPrefixName);

    Optional<Message> findById(Long messageId);

}