package ua.jupiter.service.implementation;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.jupiter.database.entity.EventType;
import ua.jupiter.database.entity.ObjectType;
import ua.jupiter.database.entity.View;
import ua.jupiter.database.entity.message.Message;
import ua.jupiter.database.entity.user.User;
import ua.jupiter.database.entity.user.UserSubscription;
import ua.jupiter.database.repository.MessageRepository;
import ua.jupiter.database.repository.SubscriptionRepository;
import ua.jupiter.dto.MessagePageDto;
import ua.jupiter.dto.create.MessageCreateEditDto;
import ua.jupiter.dto.read.MessageReadDto;
import ua.jupiter.service.interfaces.MessageService;
import ua.jupiter.service.interfaces.MetaContentService;
import ua.jupiter.util.WsSender;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Transactional(readOnly = true)
public class MessageServiceImpl implements MessageService {

    private final MetaContentService metaService;
    private final ModelMapper modelMapper;
    private final SubscriptionRepository subscriptionRepository;
    private final MessageRepository messageRepository;
    private final BiConsumer<EventType, MessageReadDto> wsSender;


    public MessageServiceImpl(
            MessageRepository messageRepository,
            MetaContentService metaService,
            ModelMapper modelMapper,
            SubscriptionRepository subscriptionRepository,
            WsSender wsSender
    ) {
        this.messageRepository = messageRepository;
        this.metaService = metaService;
        this.modelMapper= modelMapper;
        this.subscriptionRepository = subscriptionRepository;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, View.FullComment.class);
    }

    @Override
    public List<Object> findForChannels(Pageable pageable, List<String> usersId) {
        Page<User> messages = messageRepository.findByAuthorIdIn(usersId, pageable);
        return messages.stream().map(modelMapper -> messages).collect(Collectors.toList());
    }

    public MessagePageDto findForUser(Pageable pageable, User user) {
        List<User> channels = subscriptionRepository.findBySubscriber(user)
                .stream()
                .filter(UserSubscription::isActive)
                .map(UserSubscription::getChannel)
                .collect(Collectors.toList());

        channels.add(user);

        Page<Message> page = messageRepository.findByAuthorIdIn(channels, );

        return new MessagePageDto(
                page.getContent(),
                pageable.getPageNumber(),
                page.getTotalPages()
        );
    }


    @Transactional
    @Override
    public Optional<MessageReadDto> updateMessage(Long messageId, MessageReadDto messageDto) {

        return messageRepository.findById(messageId)
                .map(messageFromDb -> {
                    messageFromDb.setText(messageDto.getText());
                    metaService.fillMeta(messageFromDb);
                    return messageRepository.saveAndFlush(messageFromDb);
                }).map(modelMapper -> messageDto)
                .map(message -> {
                    wsSender.accept(EventType.UPDATE, messageDto);
                    return message;
                });
    }

    @Override
    public Optional<MessageReadDto> getMessageById(Long messageId) {
        return messageRepository.findById(messageId).map(modelMapper -> (message, Message.class));
    }


    @Transactional
    @Override
    public boolean deleteMessage(Long messageId) {
        return messageRepository.findById(messageId)
                .map(entity -> {
                    messageRepository.delete(entity);
                    messageRepository.flush();
                    MessageReadDto messageReadDto = modelMapper.map(entity);
                    wsSender.accept(EventType.REMOVE, messageReadDto);
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    @Override
    public Message createMessage(MessageCreateEditDto messageDto) {
        return Objects.requireNonNull(Optional.of(messageDto)
                .map(message -> modelMapper.map(message, Message.class))
//                .map(messageRepository::saveAndFlush)
                .map(message -> modelMapper.map(message, Message.class))
                .map(savedMessage -> {
                    metaService.fillMeta(savedMessage);
                    return messageRepository.saveAndFlush(savedMessage);
                }).orElse(null));
    }

    @Override
    public List<MessageReadDto> getListMessages(Optional<String> optionalPrefixName) {
        optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());
        return optionalPrefixName
                .map(messageRepository::findAllByTextContainingIgnoreCase)
                .orElseGet(messageRepository::findAll)
                .stream().map(message -> modelMapper.map(message, MessageReadDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Message> findById(Long messageId) {
        return messageRepository.findById(messageId);
    }


}