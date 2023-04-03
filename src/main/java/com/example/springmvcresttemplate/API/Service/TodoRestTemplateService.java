package com.example.springmvcresttemplate.API.Service;

import com.example.springmvcresttemplate.API.TodoRequest;
import com.example.springmvcresttemplate.API.TodoResponse;
import org.apache.tomcat.util.json.ParseException;

import java.util.List;

public interface TodoRestTemplateService {
    List<TodoResponse> getAllTodo() throws ParseException;

    String editTodo(TodoRequest todoRequest, Long id);

    TodoResponse getATodo(Long id);
}
