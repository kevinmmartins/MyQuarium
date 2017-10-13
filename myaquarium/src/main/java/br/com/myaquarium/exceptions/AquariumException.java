package br.com.myaquarium.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.myaquarium.exceptions.enuns.AquariumExceptions;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Some exception happens")
public class AquariumException extends Exception {

	/**
	 * Always change after make some change
	 */
	private static final long serialVersionUID = 7896011654198930793L;

	private AquariumExceptions aquariumExceptions;

	public AquariumException(AquariumExceptions aquarium, Exception e) {
		super(aquarium.toString(), e);
		this.aquariumExceptions = aquarium;
	}

	public AquariumException(AquariumExceptions aquarium) {
		super(aquarium.toString());
		this.aquariumExceptions = aquarium;
	}

	public AquariumExceptions getException() {
		return aquariumExceptions;
	}

}
