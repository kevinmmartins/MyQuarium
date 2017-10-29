package br.com.myaquarium;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.myaquarium.enums.UserConstants;
import br.com.myaquarium.model.User;

@Controller
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class AquariumDataController {

	private final static Logger logger = Logger.getLogger(AquariumDataController.class);

	@RequestMapping(value = "aquariumInstance/{username:.+}/{aquariumId}", method = RequestMethod.GET)
	public ModelAndView manageAquariumData(@PathVariable("username") String username,
			@PathVariable("aquariumId") Long aquariumId, HttpSession session, Model model) {

		User user = (User) session.getAttribute(UserConstants.User.getValue());

		if (user == null) {
			logger.info("Session user is null");
			return new ModelAndView("redirect:/");
		}
		if (!user.getUser().equals(username)) {
			logger.info("Invalid username, session user has the username: " + user.getUser());
			return new ModelAndView("redirect:/");
		}

		model.addAttribute(UserConstants.Name.getValue(), user.getFirstName());

		return new ModelAndView("aquariumData");

	}

	@RequestMapping(value = "doLogoff", method = RequestMethod.POST)
	public ModelAndView doLogoff(HttpSession session) {
		return new ModelAndView("redirect:/aquarium/aquariumList/doLogoff");

	}

}
