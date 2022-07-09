package ua.jupiter.database.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table
@JsonAutoDetect
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
    private Message message;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @JsonView(View.FullMessage.class)
    private User author;

}
