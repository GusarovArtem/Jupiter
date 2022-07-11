package ua.jupiter.database.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usr")
@Data
@EqualsAndHashCode(of = { "id" })
@ToString(of = { "id", "name" })
public class User {
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

    @JsonView(View.FullProfile.class)
    @OneToMany(
            mappedBy = "subscriber",
            orphanRemoval = true
    )
    private Set<UserSubscription> subscriptions = new HashSet<>();

    @JsonView(View.FullProfile.class)
    @OneToMany(
            mappedBy = "channel",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private Set<UserSubscription> subscribers = new HashSet<>();
}