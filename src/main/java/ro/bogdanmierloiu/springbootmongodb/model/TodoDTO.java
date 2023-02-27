package ro.bogdanmierloiu.springbootmongodb.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "todos")
public class TodoDTO {
    @Id
    private String id;
    @NotNull(message = "todo cannot be null")
    private String todo;
    @NotNull(message = "description cannot be null")
    private String description;
    @NotNull(message = "completed cannot be null")
    private Boolean completed;

    private LocalDate createdAt;
    private LocalDate updatedAt;
}
