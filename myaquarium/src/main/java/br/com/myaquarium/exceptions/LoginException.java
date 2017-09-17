package br.com.myaquarium.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.myaquarium.exceptions.enuns.LoginExceptions;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Some exception happens")
public class LoginException extends Exception{

	/**
	 * Always change after make some change
	 */
	private static final long serialVersionUID = -8140793943752001227L;
	
	private LoginExceptions exception;
	
	public LoginException(LoginExceptions login, Exception e) {
		super(login.toString(), e);
		this.exception=login;
	}

	public LoginException(LoginExceptions login) {
		super(login.toString());
		this.exception=login;
	}

	public LoginExceptions getException() {
		return exception;
	}

}
