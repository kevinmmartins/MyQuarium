package br.com.myaquarium.validations;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import br.com.myaquarium.exceptions.LoginException;
import br.com.myaquarium.exceptions.UserException;
import br.com.myaquarium.exceptions.enuns.LoginExceptions;
import br.com.myaquarium.model.User;
import br.com.myaquarium.repository.UserRepository;
import br.com.myaquarium.security.PasswordSecurity;

public class LoginValidations implements Validator {

	private UserRepository userRepository;
	private String user;
	private String password;
	private User validUser;

	public LoginValidations(UserRepository userRepository, String user, String password) {
		this.userRepository = userRepository;
		this.user = user;
		this.password = password;

	}

	@Override
	public void makeValidations()
			throws LoginException, NoSuchAlgorithmException, UnsupportedEncodingException, UserException {

		this.validUser = userRepository.findByUser(user);

		if (this.validUser == null) {
			throw new LoginException(LoginExceptions.USER_NOT_FOUND);
		}
		if (!PasswordSecurity.transformPassword(password).equals(validUser.getPassword())) {
			throw new LoginException(LoginExceptions.INVALID_PASSWORD);
		}

	}

	public User getValidUser() {
		return validUser;
	}
	
}
