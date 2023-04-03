package com.example.springmvcresttemplate.Controller;

import com.example.springmvcresttemplate.API.Service.TodoRestTemplateService;
import com.example.springmvcresttemplate.API.TodoRequest;
import com.example.springmvcresttemplate.API.TodoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.DataInput;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class TodoController {

    private TodoRestTemplateService todoRestTemplateService;
    private static List<TodoResponse> storedTodoResponseList;
    @Autowired
    public TodoController(TodoRestTemplateService todoRestTemplateService) {
        this.todoRestTemplateService = todoRestTemplateService;
    }

    @GetMapping("/")
    public ModelAndView homePage() throws ParseException {
        ModelAndView modelAndView = new ModelAndView("index");
        List<TodoResponse> todoResponseList = todoRestTemplateService.getAllTodo();
        storedTodoResponseList = todoResponseList;
        modelAndView.addObject("todoList", todoResponseList);
        log.info("TODOS: ---> "+ todoResponseList);
        return modelAndView;
    }

    @GetMapping("/edit")
    public ModelAndView editTodo(@RequestParam("id") Long id){
        TodoResponse todoResponse = todoRestTemplateService.getATodo(id);
        ModelAndView modelAndView = new ModelAndView("edit_todo");
        modelAndView.addObject("todo", todoResponse);
        return modelAndView;
    }

    //TODO: FIX POST MAPPING
    @PostMapping("/edit")
    public String saveEdit(@ModelAttribute("todo") TodoResponse todoResponse) {
        log.info("todo from form"+ todoResponse);
        TodoRequest todoRequest = new TodoRequest();
        todoRequest.setCompleted(todoResponse.getCompleted());
        todoRequest.setTodo(todoResponse.getTodo());
        String todoResponse1 = todoRestTemplateService.editTodo(todoRequest, todoResponse.getId());
        log.info("Updated Todo: ---> "+todoResponse1);
        return "redirect:/";
    }
}
