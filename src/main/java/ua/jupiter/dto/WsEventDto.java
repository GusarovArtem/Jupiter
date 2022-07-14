package ua.jupiter.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import ua.jupiter.database.entity.EventType;
import ua.jupiter.database.entity.ObjectType;
import ua.jupiter.database.entity.View;


@Data
@AllArgsConstructor
@JsonView(View.Id.class)
public class WsEventDto {

    private ObjectType objectType;

    private EventType eventType;

    @JsonRawValue
    private String body;
}
