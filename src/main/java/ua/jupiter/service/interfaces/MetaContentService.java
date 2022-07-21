package ua.jupiter.service.interfaces;

import org.springframework.stereotype.Service;
import ua.jupiter.database.entity.message.Message;
import ua.jupiter.api.dto.MetaDto;

import java.io.IOException;

@Service
public interface MetaContentService {

    void fillMeta(Message message);

    MetaDto getMeta(String url) throws IOException;

}