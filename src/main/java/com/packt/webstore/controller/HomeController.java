package com.packt.webstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {
	
@RequestMapping("/hola")
public String welcome(Model model) {
	model.addAttribute("greeting", "Welcome to Web Store!");   // request.setAttribute ("gretting","mensaje1");
	model.addAttribute("tagline", "The one and only amazing web store"); // request.setAttribute("tagline","mensaje2");
	return "welcome";
}
}