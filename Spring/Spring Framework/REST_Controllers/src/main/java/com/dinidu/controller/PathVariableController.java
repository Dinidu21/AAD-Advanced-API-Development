package com.dinidu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/path")
public class PathVariableController {

    @GetMapping("/{id}")
    public String getPathVariable(@PathVariable("id") String id) {
        return "Path Variable ID: " + id;
    }

    @GetMapping("/{name}/{age}")
    public String getPathVariableWithMultipleParams(@PathVariable("name") String name,
                                                    @PathVariable("age") int age) {
        return "Name: " + name + ", Age: " + age;
    }

    @GetMapping("/{id}/details/{type}")
    public String getPathVariableWithDetails(@PathVariable("id") String id,
                                             @PathVariable("type") String type) {
        return "ID: " + id + ", Type: " + type;
    }
}
