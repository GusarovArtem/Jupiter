package ua.jupiter.database.entity.user;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import ua.jupiter.database.entity.View;

import javax.persistence.*;

import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class UserSubscription implements Serializable {
    @EmbeddedId
    @JsonIgnore
    private UserSubscriptionId id;

    @MapsId("channelId")
    @ManyToOne
    @JsonView(View.IdName.class)
    @JsonIdentityReference
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    private User channel;

    @MapsId("subscriberId")
    @ManyToOne
    @JsonView(View.IdName.class)
    @JsonIdentityReference
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    private User subscriber;

    @JsonView(View.IdName.class)
    private boolean active;

    public UserSubscription(User channel, User subscriber) {
        this.channel = channel;
        this.subscriber = subscriber;
        this.id = new UserSubscriptionId(channel.getId(), subscriber.getId());
    }
}
