package ua.jupiter.http.dto.read.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import ua.jupiter.http.dto.read.user.UserReadDto;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentReadDto {

    Long id;

    String text;

    Long message;

    UserReadDto author;
}