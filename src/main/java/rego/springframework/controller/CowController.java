/**
 * 
 */
package rego.springframework.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import rego.springframework.dao.CowRepository;
import rego.springframework.pms_model.Cow;
import rego.springframework.service.CowService;

/**
 * @author kul_chinnasamy
 *
 */
@Controller
public class CowController {

	@Autowired
	private CowRepository cowrepository;

	@Autowired
	private CowService cowservice;

	private ArrayList<Cow> cowModelList;

	private List<String> cowrisklist = null;

	@GetMapping(value = "/")
	public String cowHome(
			@RequestParam(value = "search", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date q,
			Model model) {
		
		if(q != null) {
			cowModelList = new ArrayList<Cow>();
			System.out.println("q is :"+ q);
			cowrisklist = cowservice.atriskcows(q);
			for(String name : cowrisklist) {
				System.out.println("Cows in repository are : "+ cowrepository.findAll());
				Cow cowy = cowrepository.findByName(name);
				System.out.println(cowy.toString() + "cowy name :" + cowy.getName());
				cowModelList.add(cowy);
				System.out.println("This cow's name is :"+cowy.getName());
			}
		}
		
        model.addAttribute("search", cowModelList);
        model.addAttribute("cows", cowrepository.findAll());
		return "index";
	}
	
	@PostMapping(value = "/")
	public String addCow(@RequestParam("name") String name, 
			@RequestParam("rescued") @DateTimeFormat(pattern = "yyyy-MM-dd") Date rescued,
			@RequestParam("vaccinated") Boolean vaccinated, Model model) {

		cowservice.addCow(name, rescued, vaccinated);
		System.out.println("Name = "+name+", rescued = "+rescued+", vaccinated = "+vaccinated);
		return "redirect:/";		
	}
	
	@PostMapping(value = "/delete")
	public String deleteCow(@RequestParam("name") String name, 
			@RequestParam("id") Long id) {
		cowservice.deleteCow(name, id);
		System.out.println("Cow named = '" + name +"'was removed from our database. Hopefully he or she was adopted.");		
		return "redirect:/";
	}
	
	

}
