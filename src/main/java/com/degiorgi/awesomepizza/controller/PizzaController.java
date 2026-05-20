package com.degiorgi.awesomepizza.controller;

import java.util.List;
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
import org.springframework.web.server.ResponseStatusException;
import com.degiorgi.awesomepizza.model.Pizza;
import com.degiorgi.awesomepizza.service.PizzaService;

//Controller REST per la gestione delle pizze.
//Espone le API per creare, modificare, visualizzare ed eliminare le pizze del menu.

@RestController
@RequestMapping("/api/pizze")
public class PizzaController {

	@Autowired
	private PizzaService pizzaService;

	//Restituisce tutte le pizze disponibili
	@GetMapping
	public ResponseEntity<List<Pizza>> index() {
	    List<Pizza> pizze = pizzaService.findAll();
	    return ResponseEntity.ok(pizze);
	}

	//Restituisce una singola pizza tramite id
	@GetMapping("/{id}")
	public ResponseEntity<Pizza> show(@PathVariable Integer id) {
	    Pizza pizza = pizzaService.findById(id);
	    if (pizza == null) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	    }
	    return ResponseEntity.ok(pizza);
	}

	//Crea una nuova pizza con i dati ricevuti nel body 
	@PostMapping
	public ResponseEntity<Pizza> create(@RequestBody Pizza pizza) {
	    Pizza pizzaSalvata = pizzaService.save(pizza);
	    return ResponseEntity.status(201).body(pizzaSalvata);
	}

	// Modifica una pizza - id dall'URL, dati aggiornati dal body JSON
	@PutMapping("/{id}")
	public ResponseEntity<Pizza> update(@PathVariable Integer id, @RequestBody Pizza pizza) {
	    Pizza pizzaEsistente = pizzaService.findById(id);
	    if (pizzaEsistente == null) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	    }
	    pizza.setId(id); //per aggiornare l'esistente e non crearne una nuova
	    return ResponseEntity.ok(pizzaService.save(pizza));
	}

	//Elimina una pizza tramite id
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) { // risposta senza body
	    Pizza pizza = pizzaService.findById(id); 
	    if (pizza == null) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	    }
	    pizzaService.deleteById(id);
	    return ResponseEntity.noContent().build(); // 204 - nessun contenuto da restituire
	}

}
