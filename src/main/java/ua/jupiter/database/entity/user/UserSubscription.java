package ua.jupiter.database.entity.user;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ua.jupiter.database.entity.View;

import javax.persistence.*;

import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@ToString(of = "id")
public class UserSubscription implements Serializable {
    @EmbeddedId
    @JsonIgnore
    UserSubscriptionId id;

    @MapsId("channelId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonView(View.IdName.class)
    @JsonIdentityReference
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    User channel;

    @MapsId("subscriberId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonView(View.IdName.class)
    @JsonIdentityReference
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    User subscriber;

    @JsonView(View.IdName.class)
    boolean active;

    public UserSubscription(User channel, User subscriber) {
        this.channel = channel;
        this.subscriber = subscriber;
        this.id = new UserSubscriptionId(channel.getId(), subscriber.getId());
    }
}
