package ua.jupiter.database.entity.message;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import ua.jupiter.database.entity.Auditing;
import ua.jupiter.database.entity.View;
import ua.jupiter.database.entity.user.User;

import javax.persistence.*;


@Data
@Entity
@Builder
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( of = {"id"})
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment extends Auditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.IdName.class)
    Long id;

    @JsonView(View.IdName.class)
    String text;

    @ManyToOne
    @JoinColumn(name = "message_id")
    @JsonView(View.FullComment.class)
    @JsonIdentityReference
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    Message message;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @JsonView(View.IdName.class)
    User author;
}
