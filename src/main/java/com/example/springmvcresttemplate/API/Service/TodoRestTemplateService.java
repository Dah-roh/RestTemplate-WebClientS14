package com.example.springmvcresttemplate.API.Service;

import com.example.springmvcresttemplate.API.TodoRequest;
import com.example.springmvcresttemplate.API.TodoResponse;
import org.apache.tomcat.util.json.ParseException;

import java.util.List;

public interface TodoRestTemplateService {
    List<TodoResponse> getAllTodo() throws ParseException;

    TodoResponse editTodo(TodoRequest todoRequest, Long id) throws ParseException;

    TodoResponse getATodo(Long id);

    TodoResponse postATodo(TodoRequest todoRequest);

    void deleteWetinTodo(Long id);
}
