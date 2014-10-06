package jk.web;

import jk.web.user.User;
import jk.web.user.controllers.SignupController;

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

	@RequestMapping({"/", "/home", "/index"})
    public String home(User user, Model model) {
		model.addAttribute("yearsList", SignupController.getYearsList());
		return "home";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(HomeController.class, args);
    }
}

