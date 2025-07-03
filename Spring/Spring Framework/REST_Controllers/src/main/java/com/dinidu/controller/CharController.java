package com.dinidu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("char")
public class CharController {

    @GetMapping("item/i???")
    public String getChar() {
        return "Hello World";
    }

    @GetMapping("????/item")
    public String getChar2() {
        return "Hello World 2";
    }
}
