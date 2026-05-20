package com.degiorgi.awesomepizza;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.degiorgi.awesomepizza.model.Ordine;
import com.degiorgi.awesomepizza.model.StatoOrdine;
import com.degiorgi.awesomepizza.repository.OrdineRepository;
import com.degiorgi.awesomepizza.service.OrdineService;

//Test di unità - verifica il corretto flusso degli stati dell'ordine
@ExtendWith(MockitoExtension.class)
public class OrdineServiceTest {

	@Mock
	private OrdineRepository ordineRepository; // repository finto - non tocca il DB

	@InjectMocks
	private OrdineService ordineService; // service reale con repository finto iniettato

	// Verifica che un nuovo ordine abbia sempre stato IN_ATTESA
	@Test
	void nuovoOrdineDeveAvereStatoInAttesa() {
		Ordine ordine = new Ordine();
		when(ordineRepository.save(ordine)).thenReturn(ordine); // simulo salvataggio nel DB

		Ordine risultato = ordineService.creaOrdine(ordine);

		assertEquals(StatoOrdine.IN_ATTESA, risultato.getStato()); // verifico che lo stato sia IN_ATTESA
	}

	// Verifica che il pizzaiolo possa portare un ordine da IN_ATTESA a IN_LAVORAZIONE
	@Test
	void ordinePresoDaPizzaioloDeveEssereInLavorazione() {
		Ordine ordine = new Ordine();
		ordine.setStato(StatoOrdine.IN_ATTESA);
		when(ordineRepository.findById(1)).thenReturn(java.util.Optional.of(ordine)); // quando service trova odrine con id1, lo restituisce
		when(ordineRepository.save(ordine)).thenReturn(ordine); 

		Ordine risultato = ordineService.prendiInCarico(1);

		assertEquals(StatoOrdine.IN_LAVORAZIONE, risultato.getStato()); // verifico che lo stato sia IN_LAVORAZIONE
	}

	// Verifica che il pizzaiolo possa portare un ordine da IN_LAVORAZIONE a COMPLETATO
	@Test
	void ordineCompletatoDeveEssereCompletato() {
		Ordine ordine = new Ordine();
		ordine.setStato(StatoOrdine.IN_LAVORAZIONE);
		when(ordineRepository.findById(1)).thenReturn(java.util.Optional.of(ordine));
		when(ordineRepository.save(ordine)).thenReturn(ordine);

		Ordine risultato = ordineService.completaOrdine(1);

		assertEquals(StatoOrdine.COMPLETATO, risultato.getStato());// verifico che lo stato sia COMPLETATO
	}
}