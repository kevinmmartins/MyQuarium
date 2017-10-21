package br.com.myaquarium.to;

import br.com.myaquarium.model.Aquarium;

public class AquariumTo {

	private String aquariumName;
	private Double aquariumTemperature;
	private String aquariumEndpoint;
	private String aquariumCicle;
	private UserTo user;

	public AquariumTo() {

	}

	public AquariumTo(Aquarium aquarium) {
		if (aquarium != null) {
			this.aquariumName = aquarium.getAquariumName();
			this.aquariumTemperature = aquarium.getTemperature();
			this.aquariumEndpoint = aquarium.getAquariumEndpoint();
			this.aquariumCicle = aquarium.getCicle().toString();
			this.user = new UserTo(aquarium.getUser());
		}
	}

	public String getAquariumName() {
		return aquariumName;
	}

	public void setAquariumName(String aquariumName) {
		this.aquariumName = aquariumName;
	}

	public Double getAquariumTemperature() {
		return aquariumTemperature;
	}

	public void setAquariumTemperature(Double aquariumTemperature) {
		this.aquariumTemperature = aquariumTemperature;
	}

	public String getAquariumEndpoint() {
		return aquariumEndpoint;
	}

	public void setAquariumEndpoint(String aquariumEndpoint) {
		this.aquariumEndpoint = aquariumEndpoint;
	}

	public String getAquariumCicle() {
		return aquariumCicle;
	}

	public void setAquariumCicle(String aquariumCicle) {
		this.aquariumCicle = aquariumCicle;
	}

	public UserTo getUser() {
		return user;
	}

	public void setUser(UserTo user) {
		this.user = user;
	}

}
