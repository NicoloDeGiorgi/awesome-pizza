package com.degiorgi.awesomepizza.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.degiorgi.awesomepizza.model.Pizza;
import com.degiorgi.awesomepizza.repository.PizzaRepository;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository repository;

    // INDEX
    public List<Pizza> findAll() {
        return repository.findAll();
    }
    
    // FIND BY NOME EVENTO
    public List<Pizza> findByNomePizza(String nome) {
        return repository.findByNome(nome); 
    }

    // SHOW
    public Pizza findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    // CREATE 
    public Pizza save(Pizza pizza) {
        return repository.save(pizza);
    }
    
    // UPDATE
  	public Pizza updatePizza (Pizza pizza) {
  	   return repository.save(pizza);
  	}
  	

    // DELETE
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}