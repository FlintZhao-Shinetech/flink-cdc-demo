package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.FlinkConfig;

@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    FlinkConfig flinkConfig;
    
    @GetMapping("/testConfig")
    public Object testConfig(){
        return flinkConfig;
    }
}
