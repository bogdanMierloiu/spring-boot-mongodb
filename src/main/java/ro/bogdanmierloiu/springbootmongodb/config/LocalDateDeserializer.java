package ro.bogdanmierloiu.springbootmongodb.config;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@JsonComponent
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    private static final Logger logger = LoggerFactory.getLogger(LocalDateDeserializer.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String dateStr = jsonParser.getText();
        logger.debug("Parsing date string: {}", dateStr);
        LocalDate date = LocalDate.parse(dateStr, formatter);
        logger.debug("Parsed LocalDate: {}", date);
        return date;
    }
}

