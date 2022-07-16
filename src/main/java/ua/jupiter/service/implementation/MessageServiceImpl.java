package ua.jupiter.service.implementation;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private final MetaContentServiceImpl metaService;
    private final ModelMapper modelMapper;
    private final MessageRepository messageRepository;
    private final SubscriptionRepository subscriptionRepo;
    private final BiConsumer<EventType, MessageReadDto> wsSender;


    @Autowired
    public MessageServiceImpl(
            MessageRepository messageRepository,
            MetaContentServiceImpl metaService,
            ModelMapper modelMapper,
            SubscriptionRepository subscriptionRepo, WsSender wsSender
    ) {
        this.messageRepository = messageRepository;
        this.metaService = metaService;
        this.modelMapper= modelMapper;
        this.subscriptionRepo = subscriptionRepo;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, View.FullComment.class);
    }

    @Transactional
    public MessageReadDto updateMessage(Long messageId, MessageReadDto messageDto) {

        return messageRepository.findById(messageId)
                .map(messageFromDb -> {
                    messageFromDb.setText(messageDto.getText());
                    metaService.fillMeta(messageFromDb);
                    return messageRepository.saveAndFlush(messageFromDb);
                }).map(modelMapper -> messageDto)
                .map(message -> {
                    wsSender.accept(EventType.UPDATE, messageDto);
                    return message;
                }).get();
    }

    @Transactional
    @Override
    public Message createMessage(MessageCreateEditDto messageDto) {
        return Objects.requireNonNull(Optional.of(messageDto)
                .map(message -> modelMapper.map(message, Message.class))
                .map(message -> modelMapper.map(message, Message.class))
                .map(savedMessage -> {
                    metaService.fillMeta(savedMessage);
                    return messageRepository.saveAndFlush(savedMessage);
                }).orElse(null));
    }

    @Override
    @Transactional
    public boolean deleteMessage(Long messageId) {
        return messageRepository.findById(messageId)
                .map(entity -> {
                    messageRepository.deleteById(messageId);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Message getById(Long messageId) {
        return messageRepository.findById(messageId).get();
    }

    @Override
    public MessagePageDto getAllMessagesByAuthor(User user, Pageable pageable) {
        List<User> channels = subscriptionRepo.findBySubscriber(user)
                .stream()
                .filter(UserSubscription::isActive)
                .map(UserSubscription::getChannel)
                .collect(Collectors.toList());

        channels.add(user);

        Page<Message> page = messageRepository.findByAuthorIn(channels, pageable);

        return new MessagePageDto(
                page.getContent(),
                pageable.getPageNumber(),
                page.getTotalPages()
        );
    }

}