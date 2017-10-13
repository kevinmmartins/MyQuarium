package br.com.myaquarium.service;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.myaquarium.model.Aquarium;
import br.com.myaquarium.model.User;
import br.com.myaquarium.repository.AquariumRepository;
import br.com.myaquarium.validations.AquariumValidations;

@Service
public class AquariumService {

	@Autowired
	private AquariumRepository aquariumRepository;

	@Autowired
	private UserService userService;

	public void saveNewAquarium(String aquariumName, String aquariumEndpoint, User user) throws Exception {
		new AquariumValidations(aquariumName, aquariumEndpoint, aquariumRepository).makeValidations();
		Aquarium aquarium = new Aquarium(aquariumName, aquariumEndpoint, user);
		aquariumRepository.save(aquarium);
		Collection<Aquarium> aquariumList = user.getAquariumList();
		if (aquariumList != null) {
			aquariumList.add(aquarium);
			userService.saveUser(user);
		} else {
			HashSet<Aquarium> aquariumSet = new HashSet<Aquarium>();
			aquariumSet.add(aquarium);
			user.setAquariumList(aquariumSet);
			userService.saveUser(user);
		}
	}

}
