package com.example.springboot_es.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @Autowired


    @RequestMapping
    public String inde(){
        return "index.html";
    }
}