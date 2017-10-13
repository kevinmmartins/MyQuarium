package br.com.myaquarium.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.myaquarium.model.Aquarium;

@Repository
public interface AquariumRepository extends CrudRepository<Aquarium, Long> {

	public Aquarium findByAquariumName(String aquariumName);

}
