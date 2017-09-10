package br.com.myaquarium.exceptions.enuns;

public enum UserExceptions {
	CANNOT_CREATE_NEW_USER("Cannot create new User"),CANNOT_SEND_EMAIL("Cannot send an email"), EMAIL_ALREADY_EXISTS(
					"Email already exists"), EMAIL_CANNOT_BE_NULL("Email cannot be null"), PASSWORD_CANNOT_BE_NULL(
							"Password cannot be null"), USER_ALREADY_EXISTS("User already exists"), USER_CANNOT_BE_NULL(
									"User cannot be null"), NAME_CANNOT_BE_NULL(
											"Name cannot be null"), LASTNAME_CANNOT_BE_NULL("Last name cannot be null");

	private String message;

	UserExceptions(String messageError) {
		this.message = messageError;
	}

	public String getMessageDescription() {
		return message;
	}

}
