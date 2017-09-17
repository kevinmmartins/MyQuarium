package br.com.myaquarium;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.myaquarium.exceptions.LoginException;
import br.com.myaquarium.service.LoginService;

@Controller
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class LoginController {

	final static Logger logger = Logger.getLogger(LoginController.class);

	@Autowired
	private LoginService loginService;

	@RequestMapping(value = "doLogin", method = RequestMethod.POST)
	public String doLogin(@RequestParam("user") String user, @RequestParam("password") String password, Model model,
			RedirectAttributes redirectAttributes) {

		try {
			loginService.doLogin(user, password);
		} catch (LoginException e) {
			logger.error("Cannot do login", e);
			redirectAttributes.addFlashAttribute(e.getException().toString(), e.getException().getMessageDescription());
			return "redirect:loginError";
		} catch (Exception e) {
			logger.error("Cannot do login", e);
			return "redirect:/500.html";
		}

		return "newUser";
	}

	@RequestMapping("loginError")
	public String newUser() {
		return "loginError";
	}
}
