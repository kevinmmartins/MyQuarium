package br.com.myaquarium;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.myaquarium.exceptions.UserException;
import br.com.myaquarium.service.UserService;

@Controller
public class UserController {

	final static Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("newUser")
	public String newUser() {
		return "newUser";
	}

	/**
	 * This method is called by client to create a new user
	 * 
	 * @param email
	 * @param password
	 * @param user
	 * @param name
	 * @param lastName
	 * @return
	 * @throws UserException
	 */
	@RequestMapping(value = "newUser", method = RequestMethod.POST)
	public String saveNewUser(@RequestParam("email") String email, @RequestParam("password") String password,
			@RequestParam("user") String user, @RequestParam("name") String name,
			@RequestParam("lastName") String lastName) throws UserException {

		try {
			userService.saveNewUser(email, password, user, name, lastName);
		} catch (Exception e) {
			logger.error("Cannot create new user", e);
			return "redirect:/500.html";
		}
		return "redirect:/";
	}

}
