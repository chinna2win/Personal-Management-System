package rego.springframework.service;

import java.util.Date;
import java.util.List;

public interface CowService {
	
	List<String> atriskcows(Date rescued);
	
	void addCow(String name, Date rescued, Boolean vaccinated);
	
	void deleteCow(String name, Long id);
	

}
