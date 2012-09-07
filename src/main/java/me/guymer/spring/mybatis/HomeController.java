package me.guymer.spring.mybatis;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("mybatisHomeController")
@RequestMapping(value = "/mybatis/widgets")
public class HomeController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String widgets(Model model) {
		return "widget";
	}
	
}
