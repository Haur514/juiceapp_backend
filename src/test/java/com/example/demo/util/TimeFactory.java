package com.example.demo.util;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class TimeFactory {

    public LocalDateTime now() {
        return LocalDateTime.now();
    }

}