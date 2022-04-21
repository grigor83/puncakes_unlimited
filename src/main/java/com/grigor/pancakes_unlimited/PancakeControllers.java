package com.grigor.pancakes_unlimited;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pancakes_unlimited.com/API/pancakes")
public class PancakeControllers {
	@Autowired 
	PancakeRepo pRepo;
	@Autowired 
	IngredientRepo iRepo;
	
	
	@GetMapping
	public List<Pancake> listPancakes(){
		return pRepo.findAll();
	}
	
	@PostMapping
	public Pancake createPancake(@RequestBody @Valid Pancake p) {
		return pRepo.save(p);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePancakeById(@PathVariable long id) {
		Optional<Pancake> p = pRepo.findById(id);
	    if (!p.isPresent())
	        return new ResponseEntity<>("Pancake id is invalid", HttpStatus.NOT_FOUND);
		
		pRepo.delete(p.get());
        return new ResponseEntity<>("Pancake deleted successfully", HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pancake> findPancakeById(@PathVariable long id) {
	    Optional<Pancake> pancake = pRepo.findById(id);
		 
	    if(pancake.isPresent()) {
	        return ResponseEntity.ok().body(pancake.get());
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Pancake> updatePancake(@PathVariable long id,@RequestBody @Valid Pancake p) {	
		Pancake pancake= pRepo.save(p);	
		return new ResponseEntity<Pancake>(pancake, HttpStatus.CREATED);
	}
}
