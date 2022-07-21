package ua.jupiter.service.implementation.message;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.jupiter.api.dto.EventType;
import ua.jupiter.api.dto.ObjectType;
import ua.jupiter.api.dto.create.CommentCreateEditDto;
import ua.jupiter.api.dto.read.message.CommentReadDto;
import ua.jupiter.api.util.WsSender;
import ua.jupiter.database.entity.message.Comment;
import ua.jupiter.database.repository.CommentRepository;
import ua.jupiter.service.interfaces.CommentService;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CommentServiceImpl implements CommentService {

    ModelMapper modelMapper;

    CommentRepository commentRepository;

    BiConsumer<EventType, CommentReadDto> wsSender;

    @Autowired
    public CommentServiceImpl(ModelMapper modelMapper, CommentRepository commentRepository, WsSender wsSender) {
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;
        this.wsSender = wsSender.getSenderNew(ObjectType.COMMENT);
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
    public boolean deleteComment(Long commentId) {
        return commentRepository.findById(commentId)
                .map(entity -> {
                    commentRepository.deleteById(commentId);
                    commentRepository.flush();
                    CommentReadDto comment = modelMapper.map(entity, CommentReadDto.class);
                    wsSender.accept(EventType.REMOVE, comment);
                    return true;
                })
                .orElse(false);
    }
}