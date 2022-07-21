package ua.jupiter.api.dto.create;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentCreateEditDto {

    String text;

    Long messageId;

    String authorId;
}