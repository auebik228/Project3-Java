package com.example.SpringProject1.domain.task;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Task {
    private long id;
    private String title;
    private String description;
    private Status status;
    private LocalDateTime expirationDate;
}
