package com.degiorgi.awesomepizza.controller.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import com.degiorgi.awesomepizza.model.Pizza;
import com.degiorgi.awesomepizza.service.PizzaService;
import jakarta.validation.Valid;

//Controller Thymeleaf per la gestione delle pizze - restituisce pagine HTML
@Controller
@RequestMapping("/pizze")
public class PizzaWebController {

 @Autowired
 private PizzaService pizzaService;

 // INDEX - Lista tutte le pizze
 @GetMapping()
 public String index(Model model) {
     List<Pizza> pizze = pizzaService.findAll();
     model.addAttribute("pizze", pizze);
     return "/pizze/index";
 }

 // Filtra le pizze per nome
 @GetMapping("/findByNomePizza/{nome}")
 public String findByNomePizza(@PathVariable String nome, Model model) {
     List<Pizza> pizze;
     if (nome != null) {
         model.addAttribute("nome", nome);
         pizze = pizzaService.findByNomePizza(nome);
     } else {
         pizze = pizzaService.findAll();
     }
     model.addAttribute("pizze", pizze);
     return "/pizze/index";
 }

 // Dettaglio singola pizza
 @GetMapping("/{id}")
 public String show(@PathVariable Integer id, Model model) {
     Pizza pizza = pizzaService.findById(id);
     if (pizza == null) {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND);
     }
     model.addAttribute("pizza", pizza);
     return "pizze/show";
 }

 // Mostra form per creare una nuova pizza
 @GetMapping("/create")
 public String create(Model model) {
     model.addAttribute("pizza", new Pizza()); // oggetto vuoto per il form
     return "/pizze/create";
 }

 // Salva la nuova pizza nel DB
 @PostMapping("/create")
 public String store(@Valid @ModelAttribute("pizza") Pizza formPizza,
         BindingResult bindingResult, Model model) {
     if (bindingResult.hasErrors()) {
         return "/pizze/create";
     }
     pizzaService.save(formPizza);
     return "redirect:/pizze";
 }

 // Mostra form per modificare una pizza esistente
 @GetMapping("/edit/{id}")
 public String edit(@PathVariable Integer id, Model model) {
     model.addAttribute("pizza", pizzaService.findById(id));
     return "pizze/edit";
 }

 // Aggiorna la pizza nel DB
 @PostMapping("/edit/{id}")
 public String update(@Valid @ModelAttribute("pizza") Pizza formUpdatePizza,
         BindingResult bindingResult, Model model) {
     if (bindingResult.hasErrors()) {
         return "/pizze/edit";
     }
     pizzaService.updatePizza(formUpdatePizza);
     return "redirect:/pizze";
 }

 // Elimina una pizza
 @PostMapping("/delete/{id}")
 public String delete(@PathVariable Integer id) {
     pizzaService.deleteById(id);
     return "redirect:/pizze";
 }
}