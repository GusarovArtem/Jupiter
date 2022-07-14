package ua.jupiter.database.entity.user;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.jupiter.database.entity.View;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSubscriptionId implements Serializable {
    @JsonView(View.Id.class)
    private String channelId;

    @JsonView(View.Id.class)
    private String subscriberId;
}
