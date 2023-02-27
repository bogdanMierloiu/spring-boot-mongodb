package ro.bogdanmierloiu.springbootmongodb.service;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.bogdanmierloiu.springbootmongodb.exception.TodoCollectionException;
import ro.bogdanmierloiu.springbootmongodb.model.TodoDTO;
import ro.bogdanmierloiu.springbootmongodb.repository.TodoRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    public void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionException {
        Optional<TodoDTO> todoDTOOptional = todoRepository.findByTodo(todo.getTodo());
        if (todoDTOOptional.isPresent()) {
            throw new TodoCollectionException(TodoCollectionException.todoAlreadyExists());
        } else {
            todo.setCreatedAt(LocalDate.now());
            todoRepository.save(todo);
        }
    }

    @Override
    public List<TodoDTO> getAllTodos() {
        return todoRepository.findAll();

    }

    @Override
    public TodoDTO getSingleTodo(String id) throws TodoCollectionException {
        return todoRepository.findById(id).orElseThrow(
                () -> new TodoCollectionException(TodoCollectionException.NotFoundException(id)));
    }

    @Override
    public void updateTodo(String id, TodoDTO todo) throws TodoCollectionException {
        TodoDTO todoToUpdate = todoRepository.findById(id).orElseThrow(
                () -> new TodoCollectionException(TodoCollectionException.NotFoundException(id)));
        for (TodoDTO tody : todoRepository.findByDate(todoToUpdate.getCreatedAt())) {
            if (tody.getTodo().equals(todo.getTodo())) {
                throw new TodoCollectionException(TodoCollectionException.todoAlreadyExists());
            }
        }
        todoToUpdate.setTodo(todo.getTodo() != null ? todo.getTodo() : todoToUpdate.getTodo());
        todoToUpdate.setDescription(todo.getDescription() != null ? todo.getDescription() : todoToUpdate.getDescription());
        todoToUpdate.setCompleted(todo.getCompleted() != null ? todo.getCompleted() : todoToUpdate.getCompleted());
        todoToUpdate.setUpdatedAt(LocalDate.now());
        todoRepository.save(todoToUpdate);
    }

    @Override
    public void deleteTodo(String id) throws TodoCollectionException {
        TodoDTO todoToDelete = todoRepository.findById(id).orElseThrow(
                () -> new TodoCollectionException(TodoCollectionException.NotFoundException(id)));
        todoRepository.delete(todoToDelete);
    }

    @Override
    public List<TodoDTO> findByDate(String date) {
        LocalDate createdAt = LocalDate.parse(date);
        return todoRepository.findByDate(createdAt);
    }


}
