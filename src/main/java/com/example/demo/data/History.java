package com.example.demo.data;

import java.util.Date;

public record History(
    long id,
    String user,
    String item,
    int money,
    Date date
){}