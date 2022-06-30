package ua.jupiter.database.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@JsonAutoDetect
@Data
@Entity
@Table
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



}
