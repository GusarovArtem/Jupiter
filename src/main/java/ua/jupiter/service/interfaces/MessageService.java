package ua.jupiter.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.jupiter.database.entity.message.Message;
import ua.jupiter.dto.create.MessageCreateEditDto;
import ua.jupiter.dto.read.MessageReadDto;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    List<Object> findForChannels(Pageable pageable, List<String> usersId);

    Optional<MessageReadDto> updateMessage(Long messageId, MessageReadDto message);

    Optional<MessageReadDto> getMessageById(Long messageId);

    boolean deleteMessage(Long messageId);

    Message createMessage(MessageCreateEditDto message);

    Page<MessageReadDto> getListMessages(Optional<String> optionalPrefixName);

    Optional<Message> findById(Long messageId);

}