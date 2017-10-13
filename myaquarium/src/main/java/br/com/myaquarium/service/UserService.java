package br.com.myaquarium.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.myaquarium.email.sender.EmailSender;
import br.com.myaquarium.exceptions.UserException;
import br.com.myaquarium.model.User;
import br.com.myaquarium.repository.UserRepository;
import br.com.myaquarium.security.PasswordSecurity;
import br.com.myaquarium.validations.UserValidations;

@Service
public class UserService {


	@Autowired
	private UserRepository userRepository;

	public void saveNewUser(String email, String password, String user, String name, String lastName)
			throws NoSuchAlgorithmException, UnsupportedEncodingException, UserException {

		new UserValidations(userRepository, email, password, user, name, lastName).makeValidations();
		User newUser = new User(email, PasswordSecurity.transformPassword(password), user, name, lastName);
		userRepository.save(newUser);
		EmailSender.sendEmail(name, email);

	}
	
	public void saveUser(User user) {
		userRepository.save(user);
	}

}
