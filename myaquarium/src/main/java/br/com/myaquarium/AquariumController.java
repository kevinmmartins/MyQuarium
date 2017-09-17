package br.com.myaquarium;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import br.com.myaquarium.model.User;

@Controller
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class AquariumController {

	final static Logger logger = Logger.getLogger(AquariumController.class);

	@RequestMapping("aquarium")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "aquarium/{username}", method = RequestMethod.GET)
	@ResponseBody
	public String managePage(@PathVariable("username") String username, HttpSession session) {

		User user = (User) session.getAttribute("user");

		if (user == null) {
			logger.info("Session user is null");
			return "index";
		}
		if (!user.getUser().equals(username)) {
			logger.info("Invalid username, session user has the username: " + user.getUser());
			return "index";
		}

		return "Welcome " + username;
	}

}
