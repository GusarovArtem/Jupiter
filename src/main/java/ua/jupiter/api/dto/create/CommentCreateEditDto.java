package ua.jupiter.api.dto.create;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ua.jupiter.api.dto.read.UserReadDto;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentCreateEditDto {

    String text;

    Long messageId;

    String authorId;
}