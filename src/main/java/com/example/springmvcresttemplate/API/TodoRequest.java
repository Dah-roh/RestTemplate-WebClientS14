package com.example.springmvcresttemplate.API;

import lombok.Data;

@Data
public class TodoRequest {
    private String todo;
    private Boolean completed;
    private Long userId;
}
