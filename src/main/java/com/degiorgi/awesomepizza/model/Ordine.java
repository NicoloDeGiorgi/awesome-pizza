package com.degiorgi.awesomepizza.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ordini") 
public class Ordine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // generazione ID
	private Integer id;

	@Column
	private String codice;
	
	// IN_ATTESA, IN_LAVORAZIONE, COMPLETATO
	@Enumerated(EnumType.STRING)
	private StatoOrdine stato;
	
	@Column
	private LocalDateTime dataOrdine;
	
	//Relazione
	@ManyToMany
	private List<Pizza> pizze;
	
	
	// Getter e Setter

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public StatoOrdine getStato() {
		return stato;
	}

	public void setStato(StatoOrdine stato) {
		this.stato = stato;
	}

	public LocalDateTime getDataOrdine() {
		return dataOrdine;
	}

	public void setDataOrdine(LocalDateTime dataOrdine) {
		this.dataOrdine = dataOrdine;
	}

	public List<Pizza> getPizze() {
		return pizze;
	}

	public void setPizze(List<Pizza> pizze) {
		this.pizze = pizze;
	}

}
