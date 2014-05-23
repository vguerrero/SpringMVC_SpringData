package com.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
 
@Controller
@RequestMapping("/movies")
public class MovieController {
 
    @RequestMapping(value="/{name}", method=RequestMethod.GET)
    public String getMovie(@PathVariable String name, ModelMap model){
 
        //String message = "Hola Mundo, Spring 3.0!";
		//return new ModelAndView("hello", "message", message);
		model.addAttribute("movie", name);//agregando al modelo el nombre recibido
		return "movieList"; //esta es la vista q estoy devolviendo
    }
	
	
	//@RequestMapping("/")
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String getDefaultMovie(ModelMap model){
		model.addAttribute("movie", "this is default movie");
		return "movieList";
	}
}