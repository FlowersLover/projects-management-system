package com.digitaldesign.murashkina.dto.request.task;

import com.digitaldesign.murashkina.dto.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
public class SearchTaskRequest {
    private String executor;
    private String author;
    private Date deadline;
    private Date createdAt;
    private TaskStatus status;
}
