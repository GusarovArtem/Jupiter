package ua.jupiter.database.entity.message;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import ua.jupiter.database.entity.user.User;
import ua.jupiter.database.entity.View;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id", "text"})
@EqualsAndHashCode(of = {"id"})
@JsonIdentityInfo(
        property = "id",
        generator = ObjectIdGenerators.PropertyGenerator.class
)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(View.Id.class)
    private Long id;
    @JsonView(View.IdName.class)
    private String text;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(View.FullMessage.class)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView(View.FullMessage.class)
    private User author;

    @OneToMany(mappedBy = "message", orphanRemoval = true)
    @JsonView(View.FullMessage.class)
    private List<Comment> comments;

    @JsonView(View.FullMessage.class)
    private String link;
    @JsonView(View.FullMessage.class)
    private String linkTitle;
    @JsonView(View.FullMessage.class)
    private String linkDescription;
    @JsonView(View.FullMessage.class)
    private String linkCover;
}