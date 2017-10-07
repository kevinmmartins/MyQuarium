package br.com.myaquarium.enums;

public enum UserConstants {

	Username("userName"),Name("name"),User("user");

	private String data;

	UserConstants(String userConst) {
		this.data = userConst;
	}

	public String getValue() {
		return data;
	}

}
