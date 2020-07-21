package rego.springframework.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rego.springframework.pms_model.Cow;


@Repository
public interface CowRepository extends CrudRepository<Cow, Long> {

	Cow findByName(String name);
	
}
