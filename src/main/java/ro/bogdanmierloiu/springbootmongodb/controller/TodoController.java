package ro.bogdanmierloiu.springbootmongodb.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.bogdanmierloiu.springbootmongodb.exception.TodoCollectionException;
import ro.bogdanmierloiu.springbootmongodb.model.TodoDTO;
import ro.bogdanmierloiu.springbootmongodb.repository.TodoRepository;
import ro.bogdanmierloiu.springbootmongodb.service.TodoService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {

        this.todoService = todoService;
    }

    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodos() {
        List<TodoDTO> todos = todoService.getAllTodos();
        if (todos.size() > 0) {
            return new ResponseEntity<>(todos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No todos available", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/todos")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo) {
        try {
            todoService.createTodo(todo);
            return new ResponseEntity<>(todo, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (TodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getSingleTodo(@PathVariable("id") String id) {
        try {
            return new ResponseEntity<>(todoService.getSingleTodo(id), HttpStatus.OK);
        } catch (TodoCollectionException e) {
            return new ResponseEntity<>(TodoCollectionException.NotFoundException(id), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody TodoDTO todo) {
        try {
            todoService.updateTodo(id, todo);
            return new ResponseEntity<>(todoService.getSingleTodo(id), HttpStatus.OK);
        } catch (TodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        if (id.isEmpty() || id.isBlank()) {
            throw new IllegalArgumentException("The given id must not be null");
        }
        try {
            todoService.deleteTodo(id);
            return new ResponseEntity<>("Successfully deleted with id: " + id, HttpStatus.OK);
        } catch (TodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
