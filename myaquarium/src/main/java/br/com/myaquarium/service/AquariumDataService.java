package br.com.myaquarium.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

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

	public Collection<AquariumData> getAquariumData(Long aquariumId) {
		Aquarium aquarium = aquariumService.getAquariumById(aquariumId);
		if (aquarium == null) {
			return null;
		}
		Collection<AquariumData> aquariumData = aquarium.getAquariumData();
		if (aquariumData == null || aquariumData.size() == 0) {
			return null;
		}

		List<AquariumData> aquariumInList = new ArrayList<>(aquariumData);

		aquariumInList.sort((a, b) -> {
			if (a.getDate().isAfter(b.getDate())) {
				return -1;
			}
			if (a.getDate().isBefore(b.getDate())) {
				return 1;
			}
			return 0;
		});

		return aquariumInList;

	}

	public AquariumData getAquariumDataById(Long aquariumDataId) {
		return aquariumDataRepository.findOne(aquariumDataId);

	}

	public void delete(AquariumData aquariumDataById) {
		Aquarium aquarium = aquariumDataById.getAquarium();
		aquarium.getAquariumData().remove(aquariumDataById);
		aquariumService.saveAquarium(aquarium);
		aquariumDataRepository.delete(aquariumDataById);

	}

	public void deleteAquariumData(AquariumData data) {
		aquariumDataRepository.delete(data);
	}

}
