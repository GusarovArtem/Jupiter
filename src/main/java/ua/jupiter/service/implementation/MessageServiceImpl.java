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
import ua.jupiter.database.repository.MessageRepository;
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
    private final BiConsumer<EventType, MessageReadDto> wsSender;


    @Autowired
    public MessageServiceImpl(
            MessageRepository messageRepository,
            MetaContentServiceImpl metaService,
            ModelMapper modelMapper,
            WsSender wsSender
    ) {
        this.messageRepository = messageRepository;
        this.metaService = metaService;
        this.modelMapper= modelMapper;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, View.FullComment.class);
    }

    @Override
    public List<Object> findForChannels(Pageable pageable, List<String> usersId) {
        Page<User> messages = messageRepository.findByAuthorIdIn(usersId, pageable);
        return messages.stream().map(modelMapper -> messages).collect(Collectors.toList());
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
    public Message getMessageById(Long messageId) {
        return messageRepository.findById(messageId).orElse(null);
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

    @Override
    public List<MessageReadDto> findAllByAuthor(User author, Pageable pageable) {
        return messageRepository.findAllByAuthor(author, pageable)
                .stream().map(schedule -> modelMapper.map(schedule, MessageReadDto.class))
                .collect(Collectors.toList());
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

}