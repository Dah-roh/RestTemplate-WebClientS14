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

import java.util.*;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class TodoController {

    private TodoRestTemplateService todoRestTemplateService;
    public static List<TodoResponse> storedTodoResponseList;
    @Autowired
    public TodoController(TodoRestTemplateService todoRestTemplateService) {
        this.todoRestTemplateService = todoRestTemplateService;
    }

    @GetMapping("/")
    public ModelAndView homePage() throws ParseException {
        ModelAndView modelAndView = new ModelAndView("index");
        if (storedTodoResponseList==null){
        List<TodoResponse> todoResponseList = todoRestTemplateService.getAllTodo();
        storedTodoResponseList = todoResponseList;
        }
        modelAndView.addObject("todoList", storedTodoResponseList);
        log.info("TODOS: ---> "+ storedTodoResponseList);
        return modelAndView;
    }

    @GetMapping("/edit")
    public ModelAndView editTodo(@RequestParam("id") Long id){
        TodoResponse todoResponse = todoRestTemplateService.getATodo(id);
        ModelAndView modelAndView = new ModelAndView("edit_todo");
        modelAndView.addObject("todo", todoResponse);
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView saveEdit(@ModelAttribute("todo") TodoResponse todoResponse) throws ParseException {
        TodoRequest todoRequest = new TodoRequest();
        todoRequest.setCompleted(todoResponse.getCompleted());
        todoRequest.setTodo(todoResponse.getTodo());
        //Update our storedTodoResponseList instead of running an API call again
        TodoResponse todoResponse1 = todoRestTemplateService.editTodo(todoRequest, todoResponse.getId());
        storedTodoResponseList = storedTodoResponseList.stream().map(todos-> Objects.equals(todos.getId(), todoResponse1.getId())?todoResponse1:todos)
                .collect(Collectors.toList());
        ModelAndView modelAndView = new ModelAndView("index")
                .addObject("todoList", storedTodoResponseList);
        return modelAndView;
    }


    @GetMapping("/add")
    public ModelAndView postATodo(){
        return new ModelAndView("add_todo").addObject("todo", new TodoRequest());
    }

    @PostMapping("/add")
    public ModelAndView postWetinTodo(@ModelAttribute("todo") TodoRequest todoRequest){
        todoRequest.setUserId(48l);
        storedTodoResponseList.add(todoRestTemplateService.postATodo(todoRequest));
        return new ModelAndView("index").addObject("todoList", storedTodoResponseList);
    }

    @GetMapping("/delete")
    public String deleteWetinTodo(@RequestParam("id") Long id){
        todoRestTemplateService.deleteWetinTodo(id);
        return "redirect:/";
    }

}
