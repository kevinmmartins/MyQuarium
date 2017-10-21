package br.com.myaquarium.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.myaquarium.model.Aquarium;
import br.com.myaquarium.service.AquariumService;
import br.com.myaquarium.to.AquariumTo;

@RestController
public class AquariumApi {

	@Autowired
	private AquariumService aquariumService;

	@RequestMapping(value = "aquariumapi/{endpoint:.+}", method = RequestMethod.GET)
	public AquariumTo getAquariumByEndpoint(@PathVariable("endpoint") String endpoint) {

		Aquarium aquariumByEndpoint = aquariumService.getAquariumByEndpoint(endpoint);

		return new AquariumTo(aquariumByEndpoint);
	}

	@RequestMapping(value = "aquariumapi/aquariuns", method = RequestMethod.GET)
	public List<AquariumTo> getAllAquariuns() {

		List<Aquarium> allAquariuns = aquariumService.getAllAquariuns();

		ArrayList<AquariumTo> aquariumList = new ArrayList<>();

		if (allAquariuns != null && allAquariuns.size() > 0) {
			allAquariuns.forEach(a -> {
				AquariumTo aquariumTo = new AquariumTo(a);
				aquariumList.add(aquariumTo);
			});
		}

		return aquariumList;
	}

}
