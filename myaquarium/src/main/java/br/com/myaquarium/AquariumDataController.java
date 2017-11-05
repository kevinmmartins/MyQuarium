package br.com.myaquarium;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.myaquarium.enums.MessagesUtil;
import br.com.myaquarium.enums.UserConstants;
import br.com.myaquarium.model.AquariumData;
import br.com.myaquarium.model.User;
import br.com.myaquarium.model.general.AquariumGeneralData;
import br.com.myaquarium.service.AquariumDataService;

@Controller
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class AquariumDataController {

	private final static Logger logger = Logger.getLogger(AquariumDataController.class);
	@Autowired
	private AquariumDataService aquariumDataService;

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

		Collection<AquariumData> aquariumData = aquariumDataService.getAquariumData(aquariumId);

		if (aquariumData == null) {
			model.addAttribute(MessagesUtil.AQUARIUM_DATA_IS_EMPTY.toString(),
					MessagesUtil.AQUARIUM_DATA_IS_EMPTY.getValue());
		} else {
			List<AquariumData> aquariumList = (List<AquariumData>) aquariumData;
			model.addAttribute(UserConstants.AquariumName.getValue(),
					aquariumList.get(0).getAquarium().getAquariumName());
			model.addAttribute(UserConstants.AquariumData.getValue(), aquariumData);
			AquariumGeneralData aquariumGeneralData = new AquariumGeneralData(aquariumData);
			aquariumGeneralData.calculate();
			model.addAttribute(UserConstants.aquariumGeneral.getValue(), aquariumGeneralData);

		}

		return new ModelAndView("aquariumData");

	}

	@RequestMapping(value = "doLogoff", method = RequestMethod.POST)
	public ModelAndView doLogoff(HttpSession session) {
		return new ModelAndView("redirect:/aquarium/aquariumList/doLogoff");

	}

	@RequestMapping(value = "aquariumData/delete/{aquariumDataId}", method = RequestMethod.GET)
	public ModelAndView doLogoff(@PathVariable("aquariumDataId") Long aquariumDataId, HttpSession session,
			Model model) {

		User user = (User) session.getAttribute(UserConstants.User.getValue());

		if (user == null) {
			logger.info("Session user is null");
			return new ModelAndView("redirect:/");
		}

		AquariumData aquariumDataById = aquariumDataService.getAquariumDataById(aquariumDataId);

		if (aquariumDataById != null) {
			aquariumDataService.delete(aquariumDataById);
		}

		return new ModelAndView(
				"redirect:/aquariumInstance/" + user.getUser() + "/" + aquariumDataById.getAquarium().getId());

	}

}
