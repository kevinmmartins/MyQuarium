package br.com.myaquarium.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.myaquarium.email.sender.EmailSender;
import br.com.myaquarium.exceptions.UserException;
import br.com.myaquarium.model.Aquarium;
import br.com.myaquarium.model.User;
import br.com.myaquarium.repository.UserRepository;
import br.com.myaquarium.security.PasswordSecurity;
import br.com.myaquarium.validations.UserValidations;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AquariumService aquariumService;

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

	public void updateUser(User user, String email, String password, String name, String lastName)
			throws NoSuchAlgorithmException, UnsupportedEncodingException, UserException {

		if (email != null && !email.isEmpty()) {
			user.setEmail(email);
		}
		if (password != null && !password.isEmpty()) {
			user.setPassword(PasswordSecurity.transformPassword(password));
		}
		if (name != null && !name.isEmpty()) {
			user.setFirstName(name);
		}
		if (lastName != null && !lastName.isEmpty()) {
			user.setLastName(lastName);
		}

		userRepository.save(user);

	}

	public void deleteUser(User user) throws Exception {

		try {
			Collection<Aquarium> aquariumList = user.getAquariumList();
			if (aquariumList != null && aquariumList.size() > 0) {
				aquariumList.forEach(aquarium -> aquariumService.deleteAquarium(aquarium));
			}
			userRepository.delete(user);

		} catch (Exception e) {
			throw e;
		}

	}

}
