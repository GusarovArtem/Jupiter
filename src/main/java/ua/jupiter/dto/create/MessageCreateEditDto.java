package ua.jupiter.dto.create;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Value;
import ua.jupiter.dto.read.UserReadDto;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageCreateEditDto {

    String id;

    String text;

    UserReadDto author;
}