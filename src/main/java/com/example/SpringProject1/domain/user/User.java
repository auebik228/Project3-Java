package com.example.SpringProject1.domain.user;

import com.example.SpringProject1.domain.task.Task;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class User {
    private Long id;
    private String name;
    private String username;
    private String password;
    private String passwordConfirmation;
    private Set<Role> roles;
    private List<Task> tasks;

}
