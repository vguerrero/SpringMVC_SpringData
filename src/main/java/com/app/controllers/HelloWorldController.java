package com.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
 
@Controller
public class HelloWorldController {
 
    @RequestMapping("/hello")
    public ModelAndView helloWorld() {
 
        String message = "Hola Mundo, Spring 3.0!";
        return new ModelAndView("hello", "message", message);
    }
}