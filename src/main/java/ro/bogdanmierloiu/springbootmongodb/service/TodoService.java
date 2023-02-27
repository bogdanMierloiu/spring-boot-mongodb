package ro.bogdanmierloiu.springbootmongodb.service;

import jakarta.validation.ConstraintViolationException;
import ro.bogdanmierloiu.springbootmongodb.exception.TodoCollectionException;
import ro.bogdanmierloiu.springbootmongodb.model.TodoDTO;

import java.time.LocalDate;
import java.util.List;

public interface TodoService {

    void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionException;

    List<TodoDTO> getAllTodos();

    TodoDTO getSingleTodo(String id) throws TodoCollectionException;

    void updateTodo(String id, TodoDTO todo) throws TodoCollectionException;

    void deleteTodo(String id) throws TodoCollectionException;

    List<TodoDTO> findByDate(String date);
}
