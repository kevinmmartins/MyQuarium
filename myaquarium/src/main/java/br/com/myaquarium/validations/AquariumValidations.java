package br.com.myaquarium.validations;

import br.com.myaquarium.exceptions.AquariumException;
import br.com.myaquarium.exceptions.enuns.AquariumExceptions;
import br.com.myaquarium.repository.AquariumRepository;

public class AquariumValidations implements Validator {

	private AquariumRepository aquariumRepository;
	private String aquariumName;
	private String aquariumEndpoint;
	private Double maxTemp;
	private Double minTemp;

	public AquariumValidations(String aquariumName, String aquariumEndpoint, AquariumRepository aquariumRepository,
			Double maxTemp, Double minTemp) {
		this.aquariumEndpoint = aquariumEndpoint;
		this.aquariumName = aquariumName;
		this.aquariumRepository = aquariumRepository;
		this.maxTemp = maxTemp;
		this.minTemp = minTemp;
	}

	@Override
	public void makeValidations() throws AquariumException {
		if (aquariumName == null || aquariumName.isEmpty()) {
			throw new AquariumException(AquariumExceptions.AQUARIUM_NAME_CANNOT_BE_NULL);
		}
		if (aquariumEndpoint == null || aquariumEndpoint.isEmpty()) {
			throw new AquariumException(AquariumExceptions.AQUARIUM_ENDPOINT_CANNOT_BE_NULL);
		}
		if (aquariumRepository.findByAquariumName(aquariumName) != null) {
			throw new AquariumException(AquariumExceptions.AQUARIUM_ALREADY_EXISTS);
		}
		if (maxTemp == null) {
			throw new AquariumException(AquariumExceptions.MAXIMUM_TEMPERATUE_CANNOT_BE_EMPTY);
		}
		if (minTemp == null) {
			throw new AquariumException(AquariumExceptions.MINIMUM_TEMPERATUE_CANNOT_BE_EMPTY);
		}

	}

}
