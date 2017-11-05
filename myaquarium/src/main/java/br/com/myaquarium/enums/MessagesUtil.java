package br.com.myaquarium.enums;

public enum MessagesUtil {

	AQUARIUM_LIST_IS_EMPTY("Usuário não tem aquários cadastrados"), AQUARIUM_DATA_IS_EMPTY(
			"Não existem dados para o aquário selecionado");

	private String data;

	MessagesUtil(String msg) {
		this.data = msg;
	}

	public String getValue() {
		return data;
	}
}
