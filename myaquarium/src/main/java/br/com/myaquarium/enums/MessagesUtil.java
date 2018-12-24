package br.com.myaquarium.enums;

public enum MessagesUtil {

	AQUARIUM_LIST_IS_EMPTY("Usuario nao tem aquarios cadastrados"), AQUARIUM_DATA_IS_EMPTY(
			"Nao existem dados para o aquario selecionado");

	private String data;

	MessagesUtil(String msg) {
		this.data = msg;
	}

	public String getValue() {
		return data;
	}
}
