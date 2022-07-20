package ua.jupiter.http.dto.read.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Value;
import ua.jupiter.http.dto.read.user.UserReadDto;

import java.util.List;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageReadDto {

    Long id;

    String text;

    List<CommentReadDto> comments;

    String link;

    String linkTitle;

    String linkDescription;

    String linkCover;

    UserReadDto author;

}
