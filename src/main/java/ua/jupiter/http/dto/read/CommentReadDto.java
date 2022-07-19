package ua.jupiter.http.dto.read;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentReadDto {

    Long id;

    String text;

    Long message;

    UserReadDto author;
}