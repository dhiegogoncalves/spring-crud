package com.project.crud.controllers;

import javax.validation.Valid;

import com.project.crud.handlers.exception.NotFoundException;
import com.project.crud.models.Department;
import com.project.crud.services.DepartmentService;
import com.querydsl.core.types.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/departments")
@Log4j2
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.ok(departmentService.findAll());
        } catch (Exception e) {
            log.error("Error trying to list data.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/page")
    public ResponseEntity<?> page(@QuerydslPredicate(root = Department.class) Predicate predicate,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        try {
            return ResponseEntity.ok(departmentService.findByPage(predicate, PageRequest.of(page, size)));
        } catch (Exception e) {
            log.error("Error trying to list data.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(departmentService.findById(id));
        } catch (NotFoundException e) {
            log.error("Id " + id + " is not existed.");
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error trying to recover data.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Department department) {
        try {
            departmentService.save(department);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error trying to save data.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody Department department) {
        try {
            return ResponseEntity.ok(departmentService.update(department));
        } catch (NotFoundException e) {
            log.error("Id is not existed.");
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error trying to update data.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        try {
            departmentService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            log.error("Id " + id + " is not existed.");
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error trying to delete data.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}