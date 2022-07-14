package ua.jupiter.dto.read;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentReadDto {

    Long id;

    String text;

    Long message;

    UserReadDto author;
}