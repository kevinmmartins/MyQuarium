package br.com.myaquarium.exceptions.enuns;

public enum LoginExceptions {
	
	USER_NOT_FOUND("User not found"),INVALID_PASSWORD("Invalid password");
	
	private String message;

	LoginExceptions(String messageError) {
		this.message = messageError;
	}

	public String getMessageDescription() {
		return message;
	}
}
