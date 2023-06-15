package com.digitaldesign.murashkina.web.handler;

import com.digitaldesign.murashkina.services.exceptions.employee.*;
import com.digitaldesign.murashkina.services.exceptions.project.InvalidProjectStatusException;
import com.digitaldesign.murashkina.services.exceptions.project.ProjectIsNullException;
import com.digitaldesign.murashkina.services.exceptions.project.ProjectNotFoundException;
import com.digitaldesign.murashkina.services.exceptions.project.StatusUnaviablException;
import com.digitaldesign.murashkina.services.exceptions.task.*;
import com.digitaldesign.murashkina.services.exceptions.team.EmployeeAlreadyInTeamException;
import com.digitaldesign.murashkina.services.exceptions.team.InvalidTeamRoleException;
import com.digitaldesign.murashkina.services.exceptions.team.MemberNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AppExceptionHandler  {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }
    @ExceptionHandler(InvalidProjectStatusException.class)
    public ResponseEntity<AwesomeException> handleInvalidProjectStatusException() {
        return new ResponseEntity<>(new AwesomeException("Статус проекта может иметь значения  DRAFT, " +
                "DEVELOP," +
                "TEST," +
                "COMPLETED"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTaskStatusException.class)
    protected ResponseEntity<AwesomeException> handleInvalidTaskStatusException() {
        return new ResponseEntity<>(new AwesomeException("Статус задачи может иметь значения NEW, IN_PROGRESS, COMPLETED, CLOSED"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidTeamRoleException.class)
    protected ResponseEntity<AwesomeException> handleInvalidTeamRoleException() {
        return new ResponseEntity<>(new AwesomeException("Сотрудник может иметь в команде роли TEAMLEAD, DEVELOPER, TESTER, ANALYST"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccessToResourceDeniedException.class)
    protected ResponseEntity<AwesomeException> handleAccessDeniedException() {
        return new ResponseEntity<>(new AwesomeException("Доступ к запрошенному ресурсу запрещен"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccountAlreadyExistException.class)
    protected ResponseEntity<AwesomeException> handleAccountAlreadyExistException() {
        return new ResponseEntity<>(new AwesomeException("Пользователь с учетной записью уже существует"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmployeeDeletedException.class)
    protected ResponseEntity<AwesomeException> handleEmployeeDeletedException() {
        return new ResponseEntity<>(new AwesomeException("Нельзя изменить удаленного сотрудника"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    protected ResponseEntity<AwesomeException> handleEmployeeNotFoundException() {
        return new ResponseEntity<>(new AwesomeException("Сотрудник не найден"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmployeeIsNullException.class)
    protected ResponseEntity<AwesomeException> handleEmployeeIsNullException() {
        return new ResponseEntity<>(new AwesomeException("Сотрудник пустой"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordsNotMatchException.class)
    protected ResponseEntity<AwesomeException> handlePasswordsNotMatchException() {
        return new ResponseEntity<>(new AwesomeException("Пароли не совпадают"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StatusUnaviablException.class)
    protected ResponseEntity<AwesomeException> handleStatusUnaviablException() {
        return new ResponseEntity<>(new AwesomeException("Статус недоступен"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    protected ResponseEntity<AwesomeException> handleProjectNotFoundException() {
        return new ResponseEntity<>(new AwesomeException("Проект не найден"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProjectIsNullException.class)
    protected ResponseEntity<AwesomeException> handleProjectIsNullException() {
        return new ResponseEntity<>(new AwesomeException("Проект имеет значение null"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmployeeNotMemberOfTeamException.class)
    protected ResponseEntity<AwesomeException> handleExecutorNotMemberOfTeamException() {
        return new ResponseEntity<>(new AwesomeException("Исполнитель не является участником команды"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskIsNullException.class)
    protected ResponseEntity<AwesomeException> handleTaskIsNullException() {
        return new ResponseEntity<>(new AwesomeException("Задача имеет значание null"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    protected ResponseEntity<AwesomeException> handleTaskNotFoundException() {
        return new ResponseEntity<>(new AwesomeException("Задача не найдена"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskStatusNotAviableException.class)
    protected ResponseEntity<AwesomeException> handleTaskStatusNotAviableException() {
        return new ResponseEntity<>(new AwesomeException("Статус не доступен"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    protected ResponseEntity<AwesomeException> handleMemberNotFoundException() {
        return new ResponseEntity<>(new AwesomeException("Участник команды не найден"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmployeeAlreadyInTeamException.class)
    protected ResponseEntity<AwesomeException> handleEmployeeAlreadyInTeamException() {
        return new ResponseEntity<>(new AwesomeException("Сотрудник уже есть в команде"), HttpStatus.NOT_FOUND);
    }

    @Data
    @AllArgsConstructor
    private static class AwesomeException {
        private String message;
    }
}