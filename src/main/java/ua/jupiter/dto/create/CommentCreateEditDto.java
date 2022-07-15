package ua.jupiter.dto.create;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ua.jupiter.dto.read.UserReadDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentCreateEditDto {

    String text;

    Long messageId;

    UserReadDto author;
}