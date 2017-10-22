package br.com.myaquarium.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.myaquarium.enums.AquariumCicle;
import br.com.myaquarium.model.Aquarium;
import br.com.myaquarium.model.AquariumData;
import br.com.myaquarium.repository.AquariumDataRepository;

@Service
public class AquariumDataService {

	@Autowired
	private AquariumDataRepository aquariumDataRepository;

	@Autowired
	private AquariumService aquariumService;

	public void createNewAquariumData(Double temperature, String aquariumCicle, Aquarium aquarium) {
		AquariumData aquariumData = new AquariumData();
		aquariumData.setAquarium(aquarium);
		aquariumData.setAquariumCicle(AquariumCicle.valueOf(aquariumCicle));
		aquariumData.setTemperature(temperature);
		aquariumData.setDate(LocalDateTime.now());

		aquariumDataRepository.save(aquariumData);

		Collection<AquariumData> aquariumDataList = aquarium.getAquariumData();

		if (aquariumDataList != null) {
			aquariumDataList.add(aquariumData);
			aquariumService.saveAquarium(aquarium);
		} else {
			HashSet<AquariumData> aquariumDataSet = new HashSet<AquariumData>();
			aquariumDataSet.add(aquariumData);
			aquarium.setAquariumData(aquariumDataSet);
			aquariumService.saveAquarium(aquarium);
		}
	}

}
