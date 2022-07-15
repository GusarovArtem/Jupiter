package ua.jupiter.service.implementation;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.jupiter.database.entity.EventType;
import ua.jupiter.database.entity.ObjectType;
import ua.jupiter.database.entity.View;
import ua.jupiter.database.entity.message.Comment;
import ua.jupiter.database.repository.CommentRepository;
import ua.jupiter.dto.create.CommentCreateEditDto;
import ua.jupiter.dto.read.CommentReadDto;
import ua.jupiter.service.interfaces.CommentService;
import ua.jupiter.util.WsSender;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CommentServiceImpl implements CommentService {

    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
    private final BiConsumer<EventType, CommentReadDto> wsSender;

    public CommentServiceImpl(ModelMapper modelMapper, CommentRepository commentRepository, WsSender wsSender) {
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;
        this.wsSender = wsSender.getSender(ObjectType.COMMENT, View.FullComment.class);
    }

    @Override
    @Transactional
    public CommentReadDto createComment(CommentCreateEditDto commentDto) {
        return Objects.requireNonNull(Optional.of(commentDto)
                .map(comment -> modelMapper.map(comment, Comment.class))
                .map(commentRepository::saveAndFlush)
                .map(comment -> modelMapper.map(comment, CommentReadDto.class))
                .map(savedComment -> {
                    wsSender.accept(EventType.CREATE, savedComment);
                    return savedComment;
                }).orElse(null));
    }

    @Override
    @Transactional
    public boolean deleteComment(Long id) {
        return commentRepository.findById(id)
                .map(entity -> {
                    commentRepository.deleteById(id);
                    return true;
                })
                .orElse(false);
    }
}