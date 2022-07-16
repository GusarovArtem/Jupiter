package ua.jupiter.service.interfaces;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.jupiter.database.entity.message.Message;
import ua.jupiter.database.entity.user.User;
import ua.jupiter.dto.MessagePageDto;
import ua.jupiter.dto.create.MessageCreateEditDto;
import ua.jupiter.dto.read.MessageReadDto;

@Service
public interface MessageService {

    MessageReadDto updateMessage(Long messageId, MessageReadDto message);

    boolean deleteMessage(Long messageId);

    Message createMessage(MessageCreateEditDto message);

    Message getById(Long messageId);

    MessagePageDto getAllMessagesByAuthor(User user, Pageable pageable);

}