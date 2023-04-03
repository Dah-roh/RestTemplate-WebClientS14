package com.example.springmvcresttemplate.API;

import lombok.Data;

@Data
public class TodoResponse {
    private Long id;
    private String todo;
    private Boolean completed;
    private Long userId;
}
