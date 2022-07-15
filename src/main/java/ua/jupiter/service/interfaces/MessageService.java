package ua.jupiter.service.interfaces;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.jupiter.database.entity.message.Message;
import ua.jupiter.database.entity.user.User;
import ua.jupiter.dto.MessagePageDto;
import ua.jupiter.dto.create.MessageCreateEditDto;
import ua.jupiter.dto.read.MessageReadDto;

import java.util.List;
import java.util.Optional;

@Service
public interface MessageService {
    List<Object> findForChannels(Pageable pageable, List<String> usersId);

    Optional<MessageReadDto> updateMessage(Long messageId, MessageReadDto message);

    Message getMessageById(Long messageId);

    boolean deleteMessage(Long messageId);

    Message createMessage(MessageCreateEditDto message);

    List<MessageReadDto> getListMessages(Optional<String> optionalPrefixName);

    Optional<Message> findById(Long messageId);

    MessagePageDto findAllUserMessages(PageRequest pageRequest, String userId);
}