package br.com.myaquarium;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AquariumController {
	
	final static Logger logger = Logger.getLogger(AquariumController.class);
	
	@RequestMapping("/aquarium")
	public String index() {
		return "index";
	}

}
