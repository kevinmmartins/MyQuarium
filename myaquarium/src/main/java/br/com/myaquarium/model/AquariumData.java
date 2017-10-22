package br.com.myaquarium.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.myaquarium.enums.AquariumCicle;

@Entity(name = "aquariumData")
public class AquariumData implements Serializable {

	/**
	 * Always channge after make some change
	 */
	private static final long serialVersionUID = 7639495351351297511L;
	@Id
	@GeneratedValue
	private Long id;
	private AquariumCicle aquariumCicle;
	private Double temperature;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "aquariumName")
	private Aquarium aquarium;
	private LocalDateTime date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AquariumCicle getAquariumCicle() {
		return aquariumCicle;
	}

	public void setAquariumCicle(AquariumCicle aquariumCicle) {
		this.aquariumCicle = aquariumCicle;
	}

	public Aquarium getAquarium() {
		return aquarium;
	}

	public void etAquarium(Aquarium aquarium) {
		this.aquarium = aquarium;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public void setAquarium(Aquarium aquarium) {
		this.aquarium = aquarium;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aquariumCicle == null) ? 0 : aquariumCicle.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((temperature == null) ? 0 : temperature.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AquariumData other = (AquariumData) obj;
		if (aquariumCicle != other.aquariumCicle)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (temperature == null) {
			if (other.temperature != null)
				return false;
		} else if (!temperature.equals(other.temperature))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AquariumData [id=" + id + ", aquariumCicle=" + aquariumCicle + ", temperature=" + temperature + "]";
	}

}
