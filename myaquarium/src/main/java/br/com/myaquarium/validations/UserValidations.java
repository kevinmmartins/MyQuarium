package br.com.myaquarium.validations;

import br.com.myaquarium.exceptions.UserException;
import br.com.myaquarium.exceptions.enuns.UserExceptions;
import br.com.myaquarium.repository.UserRepository;

public class UserValidations {

	private UserRepository userRepository;
	private String email;
	private String password;
	private String user;
	private String name;
	private String lastName;

	public UserValidations(UserRepository userRepository, String email, String password, String user, String name,
			String lastName) {
		this.userRepository = userRepository;
		this.email = email;
		this.password = password;
		this.user = user;
		this.name = name;
		this.lastName = lastName;
	}

	public void makeValidations() throws UserException {
		if (email == null || email.isEmpty()) {
			throw new UserException(UserExceptions.EMAIL_CANNOT_BE_NULL);
		}
		if (password == null || password.isEmpty()) {
			throw new UserException(UserExceptions.PASSWORD_CANNOT_BE_NULL);
		}
		if (user == null || user.isEmpty()) {
			throw new UserException(UserExceptions.USER_CANNOT_BE_NULL);
		}
		if (name == null || name.isEmpty()) {
			throw new UserException(UserExceptions.NAME_CANNOT_BE_NULL);
		}
		if (lastName == null || lastName.isEmpty()) {
			throw new UserException(UserExceptions.LASTNAME_CANNOT_BE_NULL);
		}
		if (userRepository.findByEmail(email) != null) {
			throw new UserException(UserExceptions.EMAIL_ALREADY_EXISTS);
		}
		if (userRepository.findByUser(user) != null) {
			throw new UserException(UserExceptions.USER_ALREADY_EXISTS);
		}
	}

}
