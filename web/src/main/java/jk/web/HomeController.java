package jk.web;

import jk.web.user.User;
import jk.web.user.controllers.SignupController;
import jk.web.user.repository.TitleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAutoConfiguration
@ComponentScan
public class HomeController {

	@Autowired
	private TitleRepository titleRepository;

	@RequestMapping({ "/", "/home", "/index" })
	public String home(User user, Model model) {
		SignupController.signupAttributes(model, titleRepository);
		model.addAttribute("message", "home.welcome");
		return "home";
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(HomeController.class, args);
	}
}
