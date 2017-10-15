package br.com.myaquarium.exceptions.enuns;

public enum AquariumExceptions {

	AQUARIUM_NAME_CANNOT_BE_NULL("Aquarium name cannot be null"), AQUARIUM_ENDPOINT_CANNOT_BE_NULL(
			"Aquarium endpoint cannot be null"), AQUARIUM_ALREADY_EXISTS(
					"Aquarium name already exists"), MAXIMUM_TEMPERATUE_CANNOT_BE_EMPTY(
							"Maximum temperature cannot be empty"), MINIMUM_TEMPERATUE_CANNOT_BE_EMPTY(
									"Minimum temperature cannot be empty");

	private String message;

	AquariumExceptions(String messageError) {
		this.message = messageError;
	}

	public String getMessageDescription() {
		return message;
	}
}
