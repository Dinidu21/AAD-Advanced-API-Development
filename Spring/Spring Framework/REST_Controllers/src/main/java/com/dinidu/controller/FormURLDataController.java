package com.dinidu.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/formurl")
@RestController
public class FormURLDataController {

    @PostMapping("/submit")
    public String handleFormSubmission(@RequestParam Map<String, String> formData) {
        StringBuilder response = new StringBuilder("Received form data:\n");
        for (Map.Entry<String, String> entry : formData.entrySet()) {
            response.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return response.toString();

    }
}