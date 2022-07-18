package ua.jupiter.api.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonAutoDetect
@AllArgsConstructor
public class MetaDto {

    private String title;

    private String description;

    private String cover;
}