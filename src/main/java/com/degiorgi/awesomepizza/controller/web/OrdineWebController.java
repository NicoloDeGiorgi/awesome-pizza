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
import com.degiorgi.awesomepizza.model.Ordine;
import com.degiorgi.awesomepizza.service.OrdineService;
import com.degiorgi.awesomepizza.service.PizzaService;

import jakarta.validation.Valid;

//Controller Thymeleaf per la gestione degli ordini - restituisce pagine HTML

@Controller
@RequestMapping("/ordini")
public class OrdineWebController {

 @Autowired
 private OrdineService ordineService;

 @Autowired
 private PizzaService pizzaService;

 // INDEX - Lista tutti gli ordini
 @GetMapping()
 public String index(Model model) {
     List<Ordine> ordini = ordineService.findAll();
     model.addAttribute("ordini", ordini);
     return "/ordini/index";
 }

 // SHOW - Dettaglio singolo ordine
 @GetMapping("/{id}")
 public String show(@PathVariable Integer id, Model model) {
     Ordine ordine = ordineService.findById(id);
     if (ordine == null) {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND);
     }
     model.addAttribute("ordine", ordine);
     return "ordini/show";
 }

 // CREATE - Mostra form per creare un nuovo ordine
 @GetMapping("/create")
 public String create(Model model) {
     model.addAttribute("ordine", new Ordine());
     model.addAttribute("pizzeDisponibili", pizzaService.findAll());
     return "/ordini/create";
 }

 // STORE - Salva il nuovo ordine nel DB
 @PostMapping("/create")
 public String store(@Valid @ModelAttribute("ordine") Ordine formOrdine,
         BindingResult bindingResult, Model model) {
     if (bindingResult.hasErrors()) {
         return "/ordini/create";
     }
     ordineService.creaOrdine(formOrdine); // genera UUID, imposta IN_ATTESA e data
     return "redirect:/ordini";
 }

 // EDIT -  Mostra form per modificare un ordine esistente
 @GetMapping("/edit/{id}")
 public String edit(@PathVariable Integer id, Model model) {
     model.addAttribute("ordine", ordineService.findById(id));
     return "ordini/edit";
 }

 // UPDATE - Aggiorna l'ordine nel DB
 @PostMapping("/edit/{id}")
 public String update(@Valid @ModelAttribute("ordine") Ordine formUpdateOrdine,
         BindingResult bindingResult, Model model) {
     if (bindingResult.hasErrors()) {
         return "/ordini/edit";
     }
     ordineService.save(formUpdateOrdine);
     return "redirect:/ordini";
 }

 // DELETE - Elimina un ordine
 @PostMapping("/delete/{id}")
 public String delete(@PathVariable Integer id) {
     ordineService.deleteById(id);
     return "redirect:/ordini";
 }

 // Il pizzaiolo prende in carico un ordine - stato: IN_ATTESA → IN_LAVORAZIONE
 @PostMapping("/prendiincarico/{id}")
 public String prendiInCarico(@PathVariable Integer id) {
     ordineService.prendiInCarico(id);
     return "redirect:/ordini";
 }

 // Il pizzaiolo completa un ordine - stato: IN_LAVORAZIONE → COMPLETATO
 @PostMapping("/completa/{id}")
 public String completa(@PathVariable Integer id) {
     ordineService.completaOrdine(id);
     return "redirect:/ordini";
 }
}


