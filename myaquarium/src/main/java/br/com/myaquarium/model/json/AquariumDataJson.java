package br.com.myaquarium.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AquariumDataJson {

	private Double temperature;

	private String aquariumCicle;

	public AquariumDataJson() {

	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public String getAquariumCicle() {
		return aquariumCicle;
	}

	public void setAquariumCicle(String aquariumCicle) {
		this.aquariumCicle = aquariumCicle;
	}
}
