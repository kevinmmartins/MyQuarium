package br.com.myaquarium.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.myaquarium.model.AquariumData;

@Repository
public interface AquariumDataRepository extends CrudRepository<AquariumData, Long> {

}
