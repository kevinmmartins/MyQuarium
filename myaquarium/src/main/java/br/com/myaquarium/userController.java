package br.com.myaquarium;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class userController {

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/newUser")
	public String newUser() {
		return "newUser";
	}

	@RequestMapping("/newUserValidation")
	public String newUserValdation() {
		return "index";
	}
}
