package br.com.myaquarium.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import br.com.myaquarium.enums.AquariumCicle;

@Entity(name = "aquarium")
public class Aquarium implements Serializable {

	/**
	 * Always change after make some change
	 */
	private static final long serialVersionUID = -2430432920634045720L;

	@Id
	@GeneratedValue
	private Long id;
	@Column(unique = true, nullable = false)
	private String aquariumName;
	@OneToMany(mappedBy = "aquariumId", cascade = CascadeType.ALL)
	private Collection<AquariumData> aquariumData = new HashSet<AquariumData>();
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user")
	private User user;
	@Column(nullable = false, unique=true)
	private String aquariumEndpoint;
	private Double temperature;
	private AquariumCicle cicle;

	public Aquarium(String aquariumName, String aquariumEndpoint, User user, Double temperature, AquariumCicle cicle) {
		this.aquariumName = aquariumName;
		this.aquariumEndpoint = aquariumEndpoint;
		this.user = user;
		this.temperature = temperature;
		this.cicle = cicle;
	}

	public Aquarium() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAquariumName() {
		return aquariumName;
	}

	public void setAquariumName(String aquariumName) {
		this.aquariumName = aquariumName;
	}

	public Collection<AquariumData> getAquariumData() {
		return aquariumData;
	}

	public void setAquariumData(HashSet<AquariumData> aquariumData) {
		this.aquariumData = aquariumData;
	}
	
	public String getAquariumEndpoint() {
		return aquariumEndpoint;
	}

	public void setAquariumEndpoint(String aquariumEndpoint) {
		this.aquariumEndpoint = aquariumEndpoint;
	}

	public void setAquariumData(Collection<AquariumData> aquariumData) {
		this.aquariumData = aquariumData;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public AquariumCicle getCicle() {
		return cicle;
	}

	public void setCicle(AquariumCicle cicle) {
		this.cicle = cicle;
	}

	@Override
	public String toString() {
		return "Aquarium [id=" + id + ", aquariumName=" + aquariumName + ", aquariumData=" + aquariumData + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aquariumData == null) ? 0 : aquariumData.hashCode());
		result = prime * result + ((aquariumName == null) ? 0 : aquariumName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Aquarium other = (Aquarium) obj;
		if (aquariumData == null) {
			if (other.aquariumData != null)
				return false;
		} else if (!aquariumData.equals(other.aquariumData))
			return false;
		if (aquariumName == null) {
			if (other.aquariumName != null)
				return false;
		} else if (!aquariumName.equals(other.aquariumName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
