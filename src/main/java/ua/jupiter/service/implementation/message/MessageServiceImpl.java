package ua.jupiter.service.implementation.message;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.jupiter.api.dto.EventType;
import ua.jupiter.api.dto.ObjectType;
import ua.jupiter.api.util.WsSender;
import ua.jupiter.database.entity.View;
import ua.jupiter.database.entity.message.Message;
import ua.jupiter.database.entity.user.User;
import ua.jupiter.database.repository.MessageRepository;
import ua.jupiter.database.repository.UserSubscriptionRepository;
import ua.jupiter.service.interfaces.MessageService;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;


@Service
@Transactional(readOnly = true)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MessageServiceImpl implements MessageService {

    MessageRepository messageRepository;

    MetaContentServiceImpl metaService;

    BiConsumer<EventType, Message> wsSender;

    UserSubscriptionRepository userSubscriptionRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, MetaContentServiceImpl metaService, WsSender wsSender, UserSubscriptionRepository userSubscriptionRepository) {
        this.messageRepository = messageRepository;
        this.metaService = metaService;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, View.FullMessage.class);
        this.userSubscriptionRepository = userSubscriptionRepository;
    }

    @Override
    public List<Message> findForUser(User User) {
        List<User> channels = userSubscriptionRepository.findBySubscriber(User.getId());
        channels.add(User);
        return messageRepository.findByAuthorIn(channels, Sort.by("id").descending());
    }


    @Transactional
    @Override
    public Optional<Message> updateMessage(Long messageId, Message message) {

        return messageRepository.findById(messageId)
                .map(messageFromDb -> {
                    BeanUtils.copyProperties(message, messageFromDb, "id", "comments", "author", "createdAt", "modifiedAt");
                    metaService.fillMeta(messageFromDb);
                    return messageRepository.saveAndFlush(messageFromDb);
                }).map(messageFromDb -> {
                    wsSender.accept(EventType.UPDATE, messageFromDb);
                    return messageFromDb;
                });
    }

    @Override
    public Optional<Message> getMessageById(Long messageId) {
        return messageRepository.findById(messageId);
    }


    @Transactional
    @Override
    public boolean deleteMessage(Long messageId) {
        return messageRepository.findById(messageId)
                .map(entity -> {
                    messageRepository.delete(entity);
                    messageRepository.flush();
                    wsSender.accept(EventType.REMOVE, entity);
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    @Override
    public Message createMessage(Message message, User user) {
        message.setAuthor(user);
        metaService.fillMeta(message);

        return messageRepository.save(message);
    }

    @Override
    public List<Message> getListMessages(Optional<String> optionalPrefixName) {
        optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());
        return optionalPrefixName
                .map(messageRepository::findAllByTextContainingIgnoreCase)
                .orElseGet(messageRepository::findAll);
    }

    @Override
    public Optional<Message> findById(Long messageId) {
        return messageRepository.findById(messageId);
    }


}