package br.com.myaquarium.scheduled;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.myaquarium.data.update.AquariumDataupdate;
import br.com.myaquarium.model.Aquarium;
import br.com.myaquarium.service.AquariumService;

@Component
public class AquariumDataScheduled {

	private final static Logger logger = Logger.getLogger(AquariumDataScheduled.class);

	@Autowired
	private AquariumService aquariumService;
	@Autowired
	private AquariumDataupdate aquariumDataupdate;

	@Scheduled(fixedRateString = "${fixedRate.in.milliseconds}", initialDelay = 1000)
	public void getAquariumData() {
		logger.info("Starting aquarium data scheduler");
		try {
			List<Aquarium> aquariumList = aquariumService.getAllAquariuns();

			aquariumDataupdate.update(aquariumList);

			System.out.println(LocalDateTime.now());

		} catch (Exception e) {
			logger.error("Cannot update aquarium scheduler", e);
		}

		logger.info("Finishing aquarium data timer");

	}

}
