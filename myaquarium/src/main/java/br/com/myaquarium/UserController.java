package br.com.myaquarium;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.myaquarium.model.User;
import br.com.myaquarium.repository.UserRepository;
import org.apache.log4j.Logger;

@Controller
public class UserController {

	final static Logger logger = Logger.getLogger(UserController.class);
	@Autowired
	private UserRepository userRepository;

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

		User user = new User();
		user.setUser("kmatheus");
		user.setEmail("teste@test");
		user.setIsActive(Boolean.FALSE);
		user.setFirstName("Kevin");
		user.setLastName("Martins");
		user.setPassword("teste");
		userRepository.save(user);
		return "index";
	}
}
