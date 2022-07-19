package ua.jupiter.database.entity.message;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import ua.jupiter.database.entity.Auditing;
import ua.jupiter.database.entity.View;
import ua.jupiter.database.entity.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"comments"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "message")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Message extends Auditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.IdName.class)
    Long id;

    @JsonView(View.IdName.class)
    String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView(View.FullMessage.class)
    User author;

    @NotAudited
    @OneToMany(mappedBy = "message", orphanRemoval = true)
    @JsonView(View.FullMessage.class)
    List<Comment> comments = new ArrayList<>();

    @JsonView(View.FullMessage.class)
    private String link;

    @JsonView(View.FullMessage.class)
    private String linkTitle;

    @JsonView(View.FullMessage.class)
    private String linkDescription;

    @JsonView(View.FullMessage.class)
    private String linkCover;
}