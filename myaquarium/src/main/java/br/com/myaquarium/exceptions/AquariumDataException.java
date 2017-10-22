package br.com.myaquarium.exceptions;

public class AquariumDataException extends Exception {

	/**
	 * Always change after make some change
	 */
	private static final long serialVersionUID = 4211765236366154482L;

	public AquariumDataException(String error) {
		super(error);
	}
}
