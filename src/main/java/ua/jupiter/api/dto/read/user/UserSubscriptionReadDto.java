package ua.jupiter.api.dto.read.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSubscriptionReadDto {

    private String channelId;

    private String subscriberId;
}