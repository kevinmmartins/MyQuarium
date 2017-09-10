package br.com.myaquarium.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.myaquarium.exceptions.enuns.UserExceptions;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Some exception happens")
public class UserException extends Exception {

	/**
	 * Always change after make some change
	 */
	private static final long serialVersionUID = 7364445393489962584L;
	
	private UserExceptions exception;

	public UserException(UserExceptions cannotCreateNewUser, Exception e) {
		super(cannotCreateNewUser.toString(), e);
		this.exception=cannotCreateNewUser;
	}

	public UserException(UserExceptions cannotCreateNewUser) {
		super(cannotCreateNewUser.toString());
		this.exception=cannotCreateNewUser;
	}

	public UserExceptions getException() {
		return exception;
	}

}
