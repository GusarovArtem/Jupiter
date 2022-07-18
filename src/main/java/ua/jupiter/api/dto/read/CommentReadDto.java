package ua.jupiter.api.dto.read;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentReadDto {

    Long id;

    String text;

    Long message;

    UserReadDto author;
}