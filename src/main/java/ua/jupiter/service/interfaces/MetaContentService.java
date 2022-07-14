package ua.jupiter.service.interfaces;

import ua.jupiter.database.entity.message.Message;
import ua.jupiter.dto.MetaDto;

import java.io.IOException;

public interface MetaContentService {

    void fillMeta(Message message);

    MetaDto getMeta(String url) throws IOException;

}