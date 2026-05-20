package com.degiorgi.awesomepizza.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.degiorgi.awesomepizza.model.Ordine;
import com.degiorgi.awesomepizza.model.StatoOrdine;
import com.degiorgi.awesomepizza.repository.OrdineRepository;

@Service
public class OrdineService {

   @Autowired
    private OrdineRepository repository;

    // INDEX - tutti gli ordini
    public List<Ordine> findAll() {
        return repository.findAll();
    }

    // SHOW - ordine per id
    public Ordine findById(Integer id) {
        return repository.findById(id).orElse(null);
    }
    
    //UPDATE
    public Ordine save(Ordine ordine) {
        return repository.save(ordine);
    }
     
    // DELETE
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    // CODICE - cerca nel DB e lo restituisce
    public Ordine findByCodice(String codice) {
        return repository.findByCodice(codice);
    }
       
    // STATO ORDINE

    // CREATE - nuovo ordine
    public Ordine creaOrdine(Ordine ordine) {
        ordine.setCodice(UUID.randomUUID().toString()); // generazione codici univoci 
        ordine.setStato(StatoOrdine.IN_ATTESA); // modifica stato ordine
        ordine.setDataOrdine(LocalDateTime.now()); // imposta data
        return repository.save(ordine);
    }
    
    // Il pizzaiolo prende in carico
    public Ordine prendiInCarico(Integer id) {
        Ordine ordine = repository.findById(id).orElse(null);
        ordine.setStato(StatoOrdine.IN_LAVORAZIONE); // modifica stato ordine
        return repository.save(ordine);
    }

    // Il pizzaiolo completa l'ordine
    public Ordine completaOrdine(Integer id) {
        Ordine ordine = repository.findById(id).orElse(null);
        ordine.setStato(StatoOrdine.COMPLETATO); // modifica stato ordine
        return repository.save(ordine);
    }
 
}

