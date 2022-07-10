package ua.jupiter.database.entity;


import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "usr")
@Data
@EqualsAndHashCode(of = { "id" })
@ToString(of = {"id", "name"})
public class User implements Serializable {
    @Id
    @JsonView(View.IdName.class)
    private String id;
    @JsonView(View.IdName.class)
    private String name;
    @JsonView(View.IdName.class)
    private String userPicture;
    private String email;
    @JsonView(View.FullProfile.class)
    private String gender;
    @JsonView(View.FullProfile.class)
    private String locale;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(View.FullProfile.class)
    private LocalDateTime lastVisit;

    @ManyToMany
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = @JoinColumn(name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(name = "channel_id")
    )
    @JsonView(View.FullProfile.class)
    @JsonIdentityReference
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    private Set<User> subscriptions = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "subscriber_id")
    )
    @JsonView(View.FullProfile.class)
    @JsonIdentityReference
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    private Set<User> subscribers = new HashSet<>();
}