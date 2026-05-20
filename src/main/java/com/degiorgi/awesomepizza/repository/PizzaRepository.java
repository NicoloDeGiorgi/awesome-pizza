package com.degiorgi.awesomepizza.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.degiorgi.awesomepizza.model.Pizza;

//Accesso ai dati per l'entità Pizza 
public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

	List<Pizza> findByNome(String nome); 
	



}
