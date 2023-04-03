package com.example.springmvcresttemplate.API.ServiceImpl;

import com.example.springmvcresttemplate.API.Service.TodoRestTemplateService;
import com.example.springmvcresttemplate.API.TodoRequest;
import com.example.springmvcresttemplate.API.TodoResponse;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.*;

import java.util.Collections;
import java.util.List;

@Service
public class TodoRestTemplateServiceImpl implements TodoRestTemplateService {
    private RestTemplate restTemplate;

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
        List<TodoResponse> todoResponseList = (List<TodoResponse>) new JSONParser(response.getBody())
                .object().get("todos");
        return todoResponseList;
    }

    @Override
    public String editTodo(TodoRequest todoRequest, Long id){
        String url = "https://dummyjson.com/todos/"+id;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity httpEntity = new HttpEntity(todoRequest, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);
        return response.getBody();
    }

    @Override
    public TodoResponse getATodo(Long id){
        String url = "https://dummyjson.com/todos/"+id;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<TodoResponse> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, TodoResponse.class);
        return response.getBody();
    }
}
