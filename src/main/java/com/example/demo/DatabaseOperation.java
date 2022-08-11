package com.example.demo;


import java.sql.Connection;
import java.sql.Statement;

import org.springframework.stereotype.Service;

@Service
public class DatabaseOperation{
    private Connection connection = null;
    private Statement statement = null;

    public DatabaseOperation(){
    }

}