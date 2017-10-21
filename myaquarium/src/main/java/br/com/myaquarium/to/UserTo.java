package br.com.myaquarium.to;

import br.com.myaquarium.model.User;

public class UserTo {

	private String username;

	public UserTo() {

	}

	public UserTo(User user) {
		if (user != null) {
			this.username = user.getUser();
		}

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
