package com.example.todo.api.todo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.domain.model.Todo;
import com.example.todo.domain.service.todo.TodoService;
import com.github.dozermapper.core.Mapper;

@RestController
@RequestMapping("todos")
public class TodoRestController {

    @Inject
    TodoService todoService;
    @Inject
    Mapper beanMapper;

    @GetMapping // (1)
    @ResponseStatus(HttpStatus.OK) // (2)
    public List<TodoResource> getTodos() {
        Collection<Todo> todos = todoService.findAll();
        List<TodoResource> todoResources = new ArrayList<>();
        for (Todo todo : todos) {
            todoResources.add(beanMapper.map(todo, TodoResource.class)); // (3)
        }
        return todoResources; // (4)
    }
    
    @PostMapping // (1)
    @ResponseStatus(HttpStatus.CREATED) // (2)
    public TodoResource postTodos(@RequestBody @Validated TodoResource todoResource) { // (3)
        Todo createdTodo = todoService.create(beanMapper.map(todoResource, Todo.class)); // (4)
        TodoResource createdTodoResponse = beanMapper.map(createdTodo, TodoResource.class); // (5)
        return createdTodoResponse; // (6)
    }
    
    @GetMapping("{todoId}") // (1)
    @ResponseStatus(HttpStatus.OK)
    public TodoResource getTodo(@PathVariable("todoId") String todoId) { // (2)
        Todo todo = todoService.findOne(todoId); // (3)
        TodoResource todoResource = beanMapper.map(todo, TodoResource.class);
        return todoResource;
    }
    
    @PutMapping("{todoId}") // (1)
    @ResponseStatus(HttpStatus.OK)
    public TodoResource putTodo(@PathVariable("todoId") String todoId) { // (2)
        Todo finishedTodo = todoService.finish(todoId); // (3)
        TodoResource finishedTodoResource = beanMapper.map(finishedTodo, TodoResource.class);
        return finishedTodoResource;
    }
    
    @DeleteMapping("{todoId}") // (1)
    @ResponseStatus(HttpStatus.NO_CONTENT) // (2)
    public void deleteTodo(@PathVariable("todoId") String todoId) { // (3)
        todoService.delete(todoId); // (4)
    }
}