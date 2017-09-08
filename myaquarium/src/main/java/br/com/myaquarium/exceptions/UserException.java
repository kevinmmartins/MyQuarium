package br.com.myaquarium.exceptions;

import br.com.myaquarium.exceptions.enuns.UserExceptions;

public class UserException extends Exception {

	/**
	 * Always change after make some change
	 */
	private static final long serialVersionUID = 7364445393489962584L;

	public UserException(UserExceptions cannotCreateNewUser, Exception e) {
		super(cannotCreateNewUser.toString(),e);
	}

}
