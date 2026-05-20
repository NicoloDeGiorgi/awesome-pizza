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
import com.degiorgi.awesomepizza.model.Ordine;
import com.degiorgi.awesomepizza.service.OrdineService;

//Controller REST per la gestione degli ordini.
//Espone le API per creare ordini, aggiornarne lo stato e tracciarne l'avanzamento.

@RestController
@RequestMapping("/api/ordini")
public class OrdineController {

	@Autowired
	private OrdineService ordineService;

	//Restituisce tutti gli ordini 
	@GetMapping
	public ResponseEntity<List<Ordine>> index() {
		List<Ordine> ordini = ordineService.findAll();
		return ResponseEntity.ok(ordini);
	}

	//Restituisce un singolo ordine tramite id
	@GetMapping("/{id}")
	public ResponseEntity<Ordine> show(@PathVariable Integer id) {
		Ordine ordine = ordineService.findById(id);
		if (ordine == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(ordine);
	}

	//Modifica un ordine esistente. L'id indica quale
	@PutMapping("/{id}")
	public ResponseEntity<Ordine> update(@PathVariable Integer id, @RequestBody Ordine ordine) {
		Ordine ordineEsistente = ordineService.findById(id);
		if (ordineEsistente == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		ordine.setId(id); // aggiornare id esistente e non crea uno nuovo
		return ResponseEntity.ok(ordineService.save(ordine));
	}
	
	//Elimina ordine esistente
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		Ordine ordine = ordineService.findById(id);
		if (ordine == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		ordineService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	// Traccia il proprio ordine tramite codice UUID
	@GetMapping("/codice/{codice}")
	public ResponseEntity<Ordine> getByCodice(@PathVariable String codice) {
		Ordine ordine = ordineService.findByCodice(codice);
		if (ordine == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(ordine);
	}

	 // STATO ORDINE

	// Crea un nuovo ordine - il service genera UUID e imposta stato IN_ATTESA
	@PostMapping
	public ResponseEntity<Ordine> store(@RequestBody Ordine ordine) {
		Ordine ordineSalvato = ordineService.creaOrdine(ordine); // Crea ordine, stato e data
		return ResponseEntity.status(201).body(ordineSalvato); 
	}

	// Il pizzaiolo prende in carico un ordine - stato: IN_ATTESA a IN_LAVORAZIONE
	@PutMapping("/{id}/prendiincarico")
	public ResponseEntity<Ordine> carico(@PathVariable Integer id) {
		Ordine ordineInCarico = ordineService.prendiInCarico(id); //cambia stato
		if (ordineInCarico == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(ordineInCarico);
	}

	// Il pizzaiolo completa un ordine - stato: IN_LAVORAZIONE a COMPLETATO
	@PutMapping("/{id}/completa")
	public ResponseEntity<Ordine> completato(@PathVariable Integer id) {
		Ordine ordineCompletato = ordineService.completaOrdine(id); //cambia stato
		if (ordineCompletato == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(ordineCompletato);
	}

	
}