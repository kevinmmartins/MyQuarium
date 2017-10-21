package br.com.myaquarium.validations;

import br.com.myaquarium.exceptions.AquariumException;
import br.com.myaquarium.exceptions.enuns.AquariumExceptions;
import br.com.myaquarium.repository.AquariumRepository;

public class AquariumValidations implements Validator {

	private AquariumRepository aquariumRepository;
	private String aquariumName;
	private String aquariumEndpoint;
	private Double temperature;

	public AquariumValidations(String aquariumName, String aquariumEndpoint, AquariumRepository aquariumRepository,
			Double temperature) {
		this.aquariumEndpoint = aquariumEndpoint;
		this.aquariumName = aquariumName;
		this.aquariumRepository = aquariumRepository;
		this.temperature = temperature;

	}

	@Override
	public void makeValidations() throws AquariumException {
		if (aquariumName == null || aquariumName.isEmpty()) {
			throw new AquariumException(AquariumExceptions.AQUARIUM_NAME_CANNOT_BE_NULL);
		}
		if (aquariumEndpoint == null || aquariumEndpoint.isEmpty()) {
			throw new AquariumException(AquariumExceptions.AQUARIUM_ENDPOINT_CANNOT_BE_NULL);
		}
		if (aquariumRepository.findByAquariumEndpoint(aquariumEndpoint) != null) {
			throw new AquariumException(AquariumExceptions.AQUARIUM_ENDPOINT_ALREADY_EXISTS);
		}
		if (aquariumRepository.findByAquariumName(aquariumName) != null) {
			throw new AquariumException(AquariumExceptions.AQUARIUM_ALREADY_EXISTS);
		}
		if (temperature == null) {
			throw new AquariumException(AquariumExceptions.TEMPERATURE_CANNOT_BE_EMPTY);
		}

	}

}
