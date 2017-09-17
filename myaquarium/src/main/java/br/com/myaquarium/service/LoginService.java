package br.com.myaquarium.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.myaquarium.exceptions.LoginException;
import br.com.myaquarium.exceptions.UserException;
import br.com.myaquarium.repository.UserRepository;
import br.com.myaquarium.validations.LoginValidations;

@Service
public class LoginService {

	final static Logger logger = Logger.getLogger(LoginService.class);

	@Autowired
	private UserRepository userRepository;

	public void doLogin(String user, String password)
			throws NoSuchAlgorithmException, UnsupportedEncodingException, LoginException, UserException {

		new LoginValidations(userRepository, user, password).makeValidations();

	}

}
