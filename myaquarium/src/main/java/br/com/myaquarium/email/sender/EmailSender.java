package br.com.myaquarium.email.sender;

import br.com.myaquarium.exceptions.UserException;
import br.com.myaquarium.exceptions.enuns.UserExceptions;
import br.com.myaquarium.invite.EmailService;

public class EmailSender {

	/**
	 * This method is used to send a email to a new User
	 * @param name
	 * @param email
	 * @throws UserException
	 */
	public static void sendEmail(String name, String email) throws UserException {
		try {
			EmailService emailService = new EmailService();
			emailService.send(name, email);
		} catch (Exception e) {
			throw new UserException(UserExceptions.CANNOT_SEND_EMAIL);
		}
	}

}
