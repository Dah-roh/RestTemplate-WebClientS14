package com.example.springmvcresttemplate.API.ServiceImpl;

import com.example.springmvcresttemplate.API.Service.TodoRestTemplateService;
import com.example.springmvcresttemplate.API.TodoRequest;
import com.example.springmvcresttemplate.API.TodoResponse;
import com.example.springmvcresttemplate.Controller.TodoController;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TodoRestTemplateServiceImpl implements TodoRestTemplateService {
    private RestTemplate restTemplate;
    private TodoController todoController;

    @Autowired
    public TodoRestTemplateServiceImpl() {
        this.restTemplate = new RestTemplate();
    }


    @Override
    public List<TodoResponse> getAllTodo() throws ParseException {
        String url = "https://dummyjson.com/todos";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        System.out.println("Json response:-->"+response.getBody());
        List<TodoResponse> todoResponseList = new ObjectMapper().convertValue(new JSONParser(response.getBody())
                .object().get("todos"), new TypeReference<List<TodoResponse>>() {
        });
        return todoResponseList;
    }

    @Override
    public TodoResponse editTodo(TodoRequest todoRequest, Long id) throws ParseException {
        String url = "https://dummyjson.com/todos/"+id;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<TodoRequest> httpEntity = new HttpEntity(todoRequest, httpHeaders);
        ResponseEntity<TodoResponse> response = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, TodoResponse.class);
        return new ObjectMapper().convertValue(response.getBody(), TodoResponse.class);
    }

    @Override
    public TodoResponse getATodo(Long id){
        //GET storedTodoResponseList
        //find todo that matches with 'id'
        //return todo;

        //use collection STREAM to find our to-do with given id from
        // storedTodoResponseList and return a single Todo;

//        Solution:
        return TodoController.storedTodoResponseList
                .stream().filter(todo -> Objects.equals(todo.getId(), id)).findAny()
                .orElseThrow(()-> new NullPointerException("no such todo "));
    }


    @Override
    public TodoResponse postATodo(TodoRequest todoRequest){
        String url = "https://dummyjson.com/todos/add";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<TodoRequest> httpEntity = new HttpEntity<TodoRequest>(todoRequest, httpHeaders);
        ResponseEntity<TodoResponse> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, TodoResponse.class);
        return response.getBody();
    }


    @Override
    public void deleteWetinTodo(Long id){
      TodoResponse todoResponse =TodoController.storedTodoResponseList
                      .stream().filter(todo -> Objects.equals(todo.getId(), id)).findAny().orElseThrow(()-> new NullPointerException("no such todo "));
        TodoController.storedTodoResponseList.remove(todoResponse);
    }

}
