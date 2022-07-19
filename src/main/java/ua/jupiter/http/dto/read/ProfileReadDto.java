package ua.jupiter.http.dto.read;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileReadDto {

    String id;

    String name;

    String userPicture;

    String locale;

    LocalDateTime lastVisit;

    List<UserSubscriptionReadDto> subscribers;

    List<UserSubscriptionReadDto> subscriptions;

}
