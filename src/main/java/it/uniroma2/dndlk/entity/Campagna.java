package it.uniroma2.dndlk.entity;

import java.sql.Blob;

public class Campagna {
	
	private int idCampagna;
	private String nomeCampagna;
	private String descrizione;
	private int numMaxGiocatori;
	private Blob contenutoDiario;
	
	public Campagna(int idCampagna, String nomeCampagna, String descrizione, int numMaxGiocatori,
			Blob contenutoDiario) {
		super();
		this.idCampagna = idCampagna;
		this.nomeCampagna = nomeCampagna;
		this.descrizione = descrizione;
		this.numMaxGiocatori = numMaxGiocatori;
		this.contenutoDiario = contenutoDiario;
	}

	public Campagna(int idCampagna, String nomeCampagna, String descrizione, int numMaxGiocatori) {
		super();
		this.idCampagna = idCampagna;
		this.nomeCampagna = nomeCampagna;
		this.descrizione = descrizione;
		this.numMaxGiocatori = numMaxGiocatori;
	}

	@Override
	public String toString() {
		return "Campagna [idCampagna=" + idCampagna + ", nomeCampagna=" + nomeCampagna + ", descrizione=" + descrizione
				+ ", numMaxGiocatori=" + numMaxGiocatori + ", contenutoDiario=" + contenutoDiario + "]";
	}

	public int getIdCampagna() {
		return idCampagna;
	}

	public void setIdCampagna(int idCampagna) {
		this.idCampagna = idCampagna;
	}

	public Blob getContenutoDiario() {
		return contenutoDiario;
	}

	public void setContenutoDiario(Blob contenutoDiario) {
		this.contenutoDiario = contenutoDiario;
	}

	public String getNomeCampagna() {
		return nomeCampagna;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public int getNumMaxGiocatori() {
		return numMaxGiocatori;
	}
	
	
	

}
