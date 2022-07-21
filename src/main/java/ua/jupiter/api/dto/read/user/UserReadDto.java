package ua.jupiter.api.dto.read.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserReadDto {

    String id;

    String name;

    String userPicture;
}