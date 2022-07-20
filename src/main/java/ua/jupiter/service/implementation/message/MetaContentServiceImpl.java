package ua.jupiter.service.implementation.message;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import ua.jupiter.database.entity.message.Message;
import ua.jupiter.http.dto.MetaDto;
import ua.jupiter.service.interfaces.MetaContentService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MetaContentServiceImpl implements MetaContentService {

    static String URL_PATTERN = "https?:\\/\\/?[\\w\\d\\._\\-%\\/\\?=&#]+";
    static String IMAGE_PATTERN = "\\.(jpeg|jpg|gif|png)$";

    static Pattern URL_REGEX = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
    static Pattern IMG_REGEX = Pattern.compile(IMAGE_PATTERN, Pattern.CASE_INSENSITIVE);


    @Override
    public void fillMeta(Message message) {
        String text = message.getText();
        Matcher matcher = URL_REGEX.matcher(text);

        if (matcher.find()) {
            String url = text.substring(matcher.start(), matcher.end());

            matcher = IMG_REGEX.matcher(url);

            message.setLink(url);

            if (matcher.find()) {
                message.setLinkCover(url);
            } else if (!url.contains("youtu")) {
                MetaDto meta = null;
                meta = getMeta(url);


                message.setLinkCover(meta.getCover());
                message.setLinkTitle(meta.getTitle());
                message.setLinkDescription(meta.getDescription());
            }
        }
    }

    private String getContent(Element element) {
        return element == null ? "" : element.attr("content");
    }

    @SneakyThrows
    @Override
    public MetaDto getMeta(String url) {
        Document doc = Jsoup.connect(url).get();

        Elements title = doc.select("meta[name$=title],meta[property$=title]");
        Elements description = doc.select("meta[name$=description],meta[property$=description]");
        Elements cover = doc.select("meta[name$=image],meta[property$=image]");

        return new MetaDto(
                getContent(title.first()),
                getContent(description.first()),
                getContent(cover.first())
        );
    }
}