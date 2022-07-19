package ua.jupiter.database.entity.user;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ua.jupiter.database.entity.View;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"subscriptions", "subscribers"})
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usr")
public class User implements Serializable {

    @Serial
    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    @JsonView(View.IdName.class)
    String id;

    @JsonView(View.IdName.class)
    String name;

    @JsonView(View.IdName.class)
    String userPicture;
    String email;

    @JsonView(View.FullProfile.class)
    String locale;

    @JsonView(View.FullProfile.class)
    LocalDateTime lastVisit;

    @Builder.Default
    @JsonView(View.FullProfile.class)
    @OneToMany(
            mappedBy = "subscriber",
            orphanRemoval = true
    )
    private Set<UserSubscription> subscriptions = new HashSet<>();

    @Builder.Default
    @JsonView(View.FullProfile.class)
    @OneToMany(
            mappedBy = "channel",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private Set<UserSubscription> subscribers = new HashSet<>();


}