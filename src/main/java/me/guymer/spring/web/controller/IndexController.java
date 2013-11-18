package me.guymer.spring.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class IndexController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String home(Model model) {
		return "templates/home";
	}
	
	@RequestMapping(value = "/jsp", method = RequestMethod.GET)
	public String jsp() {
		return "jsp/jsp";
	}
	
}
