package com.virtualglamshop.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String,Object>> illegal(IllegalArgumentException ex){
    return problem(HttpStatus.NOT_FOUND, ex.getMessage());
  }
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String,Object>> validation(MethodArgumentNotValidException ex){
    Map<String,Object> b=new LinkedHashMap<>(); b.put("status",400); b.put("error","Validation failed");
    Map<String,String> f=new LinkedHashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(e->f.put(e.getField(), e.getDefaultMessage()));
    b.put("fields",f); return ResponseEntity.badRequest().body(b);
  }
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<Map<String,Object>> mismatch(MethodArgumentTypeMismatchException ex){
    return problem(HttpStatus.BAD_REQUEST,"Invalid parameter: "+ex.getName());
  }
  private ResponseEntity<Map<String,Object>> problem(HttpStatus s,String msg){
    Map<String,Object> b=new LinkedHashMap<>(); b.put("status",s.value()); b.put("error",msg);
    return ResponseEntity.status(s).body(b);
  }
}
