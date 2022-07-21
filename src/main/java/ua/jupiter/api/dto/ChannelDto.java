package ua.jupiter.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ChannelDto {

    List<String> channelsId;
}