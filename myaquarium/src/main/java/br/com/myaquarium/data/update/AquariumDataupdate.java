package br.com.myaquarium.data.update;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.myaquarium.exceptions.AquariumDataException;
import br.com.myaquarium.model.Aquarium;
import br.com.myaquarium.model.json.AquariumDataJson;
import br.com.myaquarium.service.AquariumDataService;

@Component
public class AquariumDataupdate {

	private final static Logger logger = Logger.getLogger(AquariumDataupdate.class);
	@Autowired
	private AquariumDataUpdateConfig aquariumDataUpdateConfig;
	@Autowired
	private AquariumDataService aquariumDataService;

	public AquariumDataupdate() {
	}

	public void update(List<Aquarium> aquariumList) {
		if (aquariumList != null && aquariumList.size() > 0) {
			logger.info("Updating " + aquariumList.size() + " aquariums");
			aquariumList.forEach(aquarium -> {
				try {
					updateAquariumData(aquarium);
				} catch (AquariumDataException e) {
					logger.error(e.getMessage());
				}
			});
		} else {
			logger.info("Nothing to update");
		}
	}

	private void updateAquariumData(Aquarium aquarium) throws AquariumDataException {
		RestTemplate restTemplate = new RestTemplate();
		AquariumDataJson aquariumJson;
		if (aquariumDataUpdateConfig.getConfIsEnable().equals(Boolean.TRUE)) {
			aquariumJson = restTemplate.getForObject(
					aquarium.getAquariumEndpoint() + "/" + aquariumDataUpdateConfig.getUriPattern(),
					AquariumDataJson.class);
		} else {
			aquariumJson = restTemplate.getForObject(aquariumDataUpdateConfig.getUriPattern(), AquariumDataJson.class);
		}
		if (aquariumJson == null) {
			throw new AquariumDataException("Cannot get the external Json");
		}
		aquariumDataService.createNewAquariumData(aquariumJson.getTemperature(), aquariumJson.getAquariumCicle(),
				aquarium);
	}

}
