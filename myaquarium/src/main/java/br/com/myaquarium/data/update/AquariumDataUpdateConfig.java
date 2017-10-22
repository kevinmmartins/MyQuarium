package br.com.myaquarium.data.update;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AquariumDataUpdateConfig {

	@Value("${aquarium.pattern}")
	private String uriPattern;
	@Value("${aquarium.is.enable}")
	private Boolean confIsEnable;
	private final static Logger logger = Logger.getLogger(AquariumDataUpdateConfig.class);

	public AquariumDataUpdateConfig() {

	}

	public String getUriPattern() {
		return uriPattern;
	}

	public Boolean getConfIsEnable() {
		return confIsEnable;
	}

	@PostConstruct
	public void print() {
		logger.info("Config data constructed");
		logger.info(getUriPattern());
		logger.info(getConfIsEnable());
	}

}
