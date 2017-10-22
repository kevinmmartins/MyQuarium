package br.com.myaquarium;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.myaquarium.enums.AquariumCicle;
import br.com.myaquarium.enums.MessagesUtil;
import br.com.myaquarium.enums.UserConstants;
import br.com.myaquarium.exceptions.AquariumException;
import br.com.myaquarium.model.User;
import br.com.myaquarium.service.AquariumService;

@Controller
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class AquariumController {

	private final static Logger logger = Logger.getLogger(AquariumController.class);

	@Autowired
	private AquariumService aquariumService;

	@RequestMapping("aquarium")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "aquarium/{username:.+}", method = RequestMethod.GET)
	public ModelAndView managePage(@PathVariable("username") String username, HttpSession session, Model model,
			RedirectAttributes redirectAttributes) {

		User user = (User) session.getAttribute(UserConstants.User.getValue());

		if (user == null) {
			logger.info("Session user is null");
			return new ModelAndView("redirect:/");
		}
		if (!user.getUser().equals(username)) {
			logger.info("Invalid username, session user has the username: " + user.getUser());
			return new ModelAndView("redirect:/");
		}

		return new ModelAndView("redirect:aquariumList/" + user.getUser());
	}

	@RequestMapping(value = "aquarium/aquariumList/{username:.+}", method = RequestMethod.GET)
	public ModelAndView manageAquarium(@PathVariable("username") String username, HttpSession session, Model model) {

		User user = (User) session.getAttribute(UserConstants.User.getValue());

		if (user == null) {
			logger.info("Session user is null");
			return new ModelAndView("redirect:/");
		}
		if (!user.getUser().equals(username)) {
			logger.info("Invalid username, session user has the username: " + user.getUser());
			return new ModelAndView("redirect:/");
		}
		if (user.getAquariumList() != null && user.getAquariumList().size() > 0) {
			model.addAttribute(UserConstants.AquariumList.getValue(), user.getAquariumList());

		} else {
			model.addAttribute(MessagesUtil.AQUARIUM_LIST_IS_EMPTY.toString(),
					MessagesUtil.AQUARIUM_LIST_IS_EMPTY.getValue());
		}

		model.addAttribute(UserConstants.Name.getValue(), user.getFirstName());

		return new ModelAndView("aquariumList");
	}

	@RequestMapping(value = "aquarium/aquariumList/doLogoff", method = RequestMethod.POST)
	public ModelAndView doLogoff(HttpSession session) {
		try {
			logger.info("Removing session user");
			session.invalidate();
			logger.info("Session user removed");
		} catch (Exception e) {
			logger.error("Cannot remove the session user", e);
		}
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "aquarium/aquariumList/newAquarium", method = RequestMethod.POST)
	public ModelAndView newAquarium(@RequestParam("aquariumName") String aquariumName,
			@RequestParam("endpoint") String endpoint, @RequestParam("temperature") Double temperature, String cicle,
			HttpSession session, RedirectAttributes redirectAttributes) {

		User user = (User) session.getAttribute(UserConstants.User.getValue());
		try {
			aquariumService.saveNewAquarium(aquariumName, endpoint, user, temperature, AquariumCicle.valueOf(cicle));
		} catch (AquariumException e) {
			logger.error("Cannot create new aquarium", e);
			redirectAttributes.addFlashAttribute(e.getException().toString(), e.getException().getMessageDescription());
			return new ModelAndView("redirect:/aquarium/aquariumList/" + user.getUser());
		} catch (Exception e) {
			logger.error("Cannot create new aquarium", e);
			return new ModelAndView("redirect:/500.html");
		}
		return new ModelAndView("redirect:/aquarium/" + user.getUser());
	}

}
