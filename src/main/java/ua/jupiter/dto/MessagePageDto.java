package ua.jupiter.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import ua.jupiter.database.entity.Message;
import ua.jupiter.database.entity.View;

import java.util.List;

@Data
@AllArgsConstructor
@JsonView(View.FullMessage.class)
public class MessagePageDto {
 private List<Message> messages;
 private int currentPage;
 private int totalPages;
}
