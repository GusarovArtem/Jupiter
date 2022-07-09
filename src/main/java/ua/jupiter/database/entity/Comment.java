package ua.jupiter.database.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Data
@EqualsAndHashCode(of = { "id" })
public class Comment {
    @Id
    @GeneratedValue
    @JsonView(View.IdName.class)
    private Long id;

    @JsonView(View.IdName.class)
    private String text;

    @ManyToOne
    @JoinColumn(name = "message_id")
    @JsonView(View.FullComment.class)
    private Message message;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @JsonView(View.IdName.class)
    private User author;
}
