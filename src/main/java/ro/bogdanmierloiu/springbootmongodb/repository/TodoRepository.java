package ro.bogdanmierloiu.springbootmongodb.repository;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ro.bogdanmierloiu.springbootmongodb.config.LocalDateDeserializer;
import ro.bogdanmierloiu.springbootmongodb.model.TodoDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends MongoRepository<TodoDTO, String> {

    @Query("{'todo' : ?0}")
    Optional<TodoDTO> findByTodo(String todo);

    @Query("{'createdAt' : ?0}")
    List<TodoDTO> findByDate(@JsonDeserialize(using = LocalDateDeserializer.class) LocalDate createdAt);






}
