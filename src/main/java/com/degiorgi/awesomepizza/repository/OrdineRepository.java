package com.degiorgi.awesomepizza.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.degiorgi.awesomepizza.model.Ordine;

//Accesso ai dati per l'entità Ordine 
public interface OrdineRepository extends JpaRepository <Ordine, Integer>{

	Ordine findByCodice(String codice); // cerca un ordine tramite codice UUID

}
