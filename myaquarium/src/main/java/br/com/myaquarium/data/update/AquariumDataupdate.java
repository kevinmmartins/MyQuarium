package br.com.myaquarium.data.update;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.myaquarium.model.Aquarium;
import br.com.myaquarium.model.json.AquariumDataJson;

@Component
public class AquariumDataupdate {

	private final static Logger logger = Logger.getLogger(AquariumDataupdate.class);
	@Autowired
	private AquariumDataUpdateConfig aquariumDataUpdateConfig;

	public AquariumDataupdate() {
	}

	public void update(List<Aquarium> aquariumList) {
		if (aquariumList != null && aquariumList.size() > 0) {
			logger.info("Updating " + aquariumList.size() + " aquariums");
			aquariumList.forEach(aquarium -> updateAquariumData(aquarium));
		} else {
			logger.info("Nothing to update");
		}
	}

	private void updateAquariumData(Aquarium aquarium) {
		RestTemplate restTemplate = new RestTemplate();
		if (aquariumDataUpdateConfig.getConfIsEnable().equals(Boolean.TRUE)) {
			AquariumDataJson aquariumJson = restTemplate.getForObject(
					aquarium.getAquariumEndpoint() + "/" + aquariumDataUpdateConfig.getUriPattern(),
					AquariumDataJson.class);
		} else {
			AquariumDataJson aquariumJson = restTemplate.getForObject(aquariumDataUpdateConfig.getUriPattern(),
					AquariumDataJson.class);
		}
	}

}
