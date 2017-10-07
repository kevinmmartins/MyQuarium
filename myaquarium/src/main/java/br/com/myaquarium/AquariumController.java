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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.myaquarium.enums.UserConstants;
import br.com.myaquarium.model.User;

@Controller
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class AquariumController {

	final static Logger logger = Logger.getLogger(AquariumController.class);

	@RequestMapping("aquarium")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "aquarium/{username:.+}", method = RequestMethod.GET)
	public String managePage(@PathVariable("username") String username, HttpSession session, Model model,
			RedirectAttributes redirectAttributes) {

		User user = (User) session.getAttribute(UserConstants.User.getValue());

		if (user == null) {
			logger.info("Session user is null");
			return "index";
		}
		if (!user.getUser().equals(username)) {
			logger.info("Invalid username, session user has the username: " + user.getUser());
			return username + " is not the session user !";
		}
		redirectAttributes.addFlashAttribute(UserConstants.Name.getValue(), user.getFirstName());

		return "redirect:aquariumList/" + user.getUser();
	}

	@RequestMapping(value = "aquarium/aquariumList/{username:.+}", method = RequestMethod.GET)
	public String manageAquarium() {
		return "aquariumList";
	}

	@RequestMapping(value = "aquarium/aquariumList/doLogoff", method = RequestMethod.POST)
	public ModelAndView doLogoff(HttpSession session) {
		try {
			logger.info("Removing session user");
			session.removeAttribute(UserConstants.User.getValue());
			logger.info("Session user removed");
		} catch (Exception e) {
			logger.error("Cannot remove the session user", e);
		}
		return new ModelAndView("redirect:/");
	}

}
