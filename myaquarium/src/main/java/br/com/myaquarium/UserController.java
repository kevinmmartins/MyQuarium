package br.com.myaquarium;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.myaquarium.exceptions.UserException;
import br.com.myaquarium.exceptions.enuns.UserExceptions;
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

	@RequestMapping("newUser")
	public String newUser() {
		return "newUser";
	}

	@RequestMapping(value = "newUser", method = RequestMethod.POST)
	public String saveNewUser(@RequestParam("email") String email, @RequestParam("senha") String password,
			@RequestParam("usuario") String user, @RequestParam("nome") String name,
			@RequestParam("sobrenome") String lastName) throws UserException {

		try {
			User newUser = new User(email, password, user, name, lastName);
			userRepository.save(newUser);
		} catch (Exception e) {
			logger.error("Cannot create new user", e);
			throw new UserException(UserExceptions.CANNOT_CREATE_NEW_USER, e);
		}
		return "index";
	}
}
